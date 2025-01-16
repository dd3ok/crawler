package com.blog.crawler.infrastructure.crawler;

import com.blog.crawler.common.exception.CrawlerException;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.CrawlingTarget;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class BaseCrawler {
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;
    private static final int DEFAULT_WAIT_TIME = 2000;
    protected final PlaywrightManager playwrightManager;

    protected BaseCrawler(PlaywrightManager playwrightManager) {
        this.playwrightManager = playwrightManager;
    }

    // 템플릿 메서드
    public final List<Content> crawlWithRetry(CrawlingTarget target, int page) {
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                log.info("Crawling attempt {} for site: {}, page: {}",
                        retryCount + 1, target.getSite(), page);

                return doCrawl(target, page);

            } catch (Exception e) {
                retryCount++;
                log.warn("Crawling failed attempt {}: {}", retryCount, e.getMessage());

                if (retryCount == MAX_RETRIES) {
                    throw new CrawlerException("크롤링 실패: " + target.getUrl(), e);
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS * retryCount); // 점진적 대기 시간 증가
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new CrawlerException("크롤링 중단", ie);
                }
            }
        }
        throw new CrawlerException("최대 재시도 횟수 초과");
    }

    // 공통 대기 로직
    protected void waitForLoad(Page page) {
        page.waitForLoadState();
        try {
            Thread.sleep(DEFAULT_WAIT_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CrawlerException("페이지 로딩 대기 중 중단됨", e);
        }
    }

    protected abstract List<Content> doCrawl(CrawlingTarget target, int page);
}