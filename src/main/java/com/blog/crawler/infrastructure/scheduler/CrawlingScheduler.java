package com.blog.crawler.infrastructure.scheduler;

import com.blog.crawler.application.port.in.CrawlingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class CrawlingScheduler {
    private final CrawlingUseCase crawlingUseCase;

    @Scheduled(fixedRateString = "${crawler.schedule.interval}")
    public void crawl() {
        crawlingUseCase.crawlLatestPosts();
    }
}