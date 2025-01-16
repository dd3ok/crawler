package com.blog.crawler.infrastructure.crawler;

import com.blog.crawler.application.port.out.CrawlingPort;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.CrawlingTarget;
import com.blog.crawler.domain.crawler.Site;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CrawlingAdapter implements CrawlingPort {
    private final Map<Site, BaseCrawler> crawlerMap;

    @Autowired
    public CrawlingAdapter(Collection<BaseCrawler> crawlers) {
        Map<Site, BaseCrawler> tempMap = new HashMap<>();

        for (BaseCrawler crawler : crawlers) {
            Site site = determineSite(crawler);
            if (site != null) {
                tempMap.put(site, crawler);
            }
        }

        this.crawlerMap = Collections.unmodifiableMap(tempMap);
    }

    private Site determineSite(BaseCrawler crawler) {
        try {
            String crawlerName = crawler.getClass().getSimpleName()
                    .replace("Crawler", "")
                    .toUpperCase();
            return Site.valueOf(crawlerName);
        } catch (IllegalArgumentException e) {
            log.warn("Cannot determine site for crawler: {}", crawler.getClass().getSimpleName());
            return null;
        }
    }

    @Override
    public List<Content> extractContents(CrawlingTarget target, int page) {
        BaseCrawler crawler = crawlerMap.get(target.getSite());
        if (crawler == null) {
            throw new IllegalArgumentException("Unsupported site: " + target.getSite());
        }
        return crawler.crawlWithRetry(target, page);
    }
}
