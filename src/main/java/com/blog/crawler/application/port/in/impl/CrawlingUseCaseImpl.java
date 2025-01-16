package com.blog.crawler.application.port.in.impl;

import com.blog.crawler.application.port.in.CrawlingUseCase;
import com.blog.crawler.application.port.out.CrawlingPort;
import com.blog.crawler.application.port.out.MessageQueuePort;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.CrawlingTarget;
import com.blog.crawler.infrastructure.crawler.config.CrawlerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CrawlingUseCaseImpl implements CrawlingUseCase {
    private final CrawlingPort crawlingPort;
    private final MessageQueuePort messageQueuePort;
    private final CrawlerProperties crawlerProperties;

    @Override
    public CompletableFuture<List<Content>> crawl(CrawlingTarget target) {
        return CompletableFuture.supplyAsync(() -> {
            List<Content> allContents = new ArrayList<>();

            for (int page = target.getPageStart(); page <= target.getPageEnd(); page++) {
                List<Content> pageContents = crawlingPort.extractContents(target, page);
                pageContents.forEach(messageQueuePort::sendContent);
                allContents.addAll(pageContents);
            }

            return allContents;
        });
    }

    @Override
    public CompletableFuture<List<Content>> crawlLatestPosts() {
        return CompletableFuture.supplyAsync(() -> {
            List<CrawlingTarget> targets = crawlerProperties.getTargets().stream()
                    .map(target -> CrawlingTarget.builder()
                            .site(target.getSite())
                            .boardId(target.getBoardId())
                            .url(target.getUrl())
                            .pageStart(1)
                            .pageEnd(1).build())
                    .toList();

            return targets.stream()
                    .map(target -> crawl(target).join())
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        });
    }
}