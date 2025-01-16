package com.blog.crawler.infrastructure.persistence;

import com.blog.crawler.domain.crawler.Site;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteAggregationResult {
    private Site site;
    private int contentCount;
    private LocalDateTime lastCrawledAt;
    private double averageViewCount;
    private double averageCommentCount;
}
