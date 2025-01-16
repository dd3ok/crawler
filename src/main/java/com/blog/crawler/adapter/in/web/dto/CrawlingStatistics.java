package com.blog.crawler.adapter.in.web.dto;

import com.blog.crawler.domain.crawler.Site;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class CrawlingStatistics {
    private Map<Site, SiteStatistics> siteStats;
    private int totalContentCount;
    private LocalDateTime lastCrawledAt;

    @Getter
    @Builder
    public static class SiteStatistics {
        private int contentCount;
        private LocalDateTime lastCrawledAt;
        private double averageViewCount;
        private double averageCommentCount;
    }
}