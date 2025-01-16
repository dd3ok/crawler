package com.blog.crawler.infrastructure.crawler.impl;

import com.blog.crawler.common.exception.CrawlerException;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.ContentMapper;
import com.blog.crawler.domain.crawler.CrawlingTarget;
import com.blog.crawler.domain.crawler.Site;
import com.blog.crawler.infrastructure.crawler.BaseCrawler;
import com.blog.crawler.infrastructure.crawler.PlaywrightManager;
import com.blog.crawler.infrastructure.persistence.entity.ContentEntity;
import com.blog.crawler.infrastructure.persistence.repository.MongoContentRepository;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NateCrawler extends BaseCrawler {
    private static final int WAIT_TIMEOUT = 30000;
    private final MongoContentRepository contentRepository;
    private final ContentMapper contentMapper;

    public NateCrawler(PlaywrightManager playwrightManager,
                       MongoContentRepository contentRepository,
                       ContentMapper contentMapper) {
        super(playwrightManager);
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    @Scheduled(fixedRate = 600000) // 10분마다 실행
    public void scheduledCrawl() {
        try {
            CrawlingTarget target = CrawlingTarget.builder()
                    .site(Site.NATE_NEWS)
                    .url("https://news.nate.com/")
                    .build();

            List<Content> newContents = crawlWithRetry(target, 1);
            saveNewContents(newContents);
        } catch (Exception e) {
            log.error("Scheduled Nate news crawling failed: {}", e.getMessage(), e);
        }
    }

    private void saveNewContents(List<Content> contents) {
        for (Content content : contents) {
            if (!contentRepository.existsBySiteAndTitle(content.getSite(), content.getTitle())) {
                ContentEntity entity = contentMapper.toEntity(content);
                contentRepository.save(entity);
                log.info("New Nate news saved: {}", content.getTitle());
            }
        }
    }
    @Override
    protected List<Content> doCrawl(CrawlingTarget target, int page) {
        Page browserPage = null;
        try {
            browserPage = playwrightManager.createPage();
            browserPage.setDefaultTimeout(WAIT_TIMEOUT);

            String url = target.getUrl();
            browserPage.navigate(url, new Page.NavigateOptions()
                    .setWaitUntil(WaitUntilState.NETWORKIDLE));

            waitForLoad(browserPage);

            // 각각의 뉴스 텍스트를 개별적으로 추출
            List<String> newsTexts = extractBubbleTexts(browserPage);

            // 각 뉴스 텍스트를 개별 Content로 변환
            return newsTexts.stream()
                    .map(text -> Content.builder()
                            .site(target.getSite())
                            .title(text.trim())
                            .url(url)
                            .postDate(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error crawling Nate News: {}", e.getMessage(), e);
            throw new CrawlerException("Failed to crawl Nate News", e);
        } finally {
            if (browserPage != null) {
                playwrightManager.closePage(browserPage);
            }
        }
    }

    private List<String> extractBubbleTexts(Page page) {
        try {
            // 각각의 bubbleText를 개별적으로 추출
            String script =
                    "Array.from(document.querySelectorAll('.bubbleText'))" +
                            ".map(bubble => {" +
                            "    return Array.from(bubble.querySelectorAll('.lineBreak'))" +
                            "        .map(tspan => tspan.textContent.trim())" +
                            "        .join('')" +
                            "})";

            return (List<String>) page.evaluate(script);
        } catch (Exception e) {
            log.warn("Error extracting bubble texts: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
