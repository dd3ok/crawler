package com.blog.crawler.application.port.in;

import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.CrawlingTarget;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CrawlingUseCase {
    CompletableFuture<List<Content>> crawl(CrawlingTarget target);
    CompletableFuture<List<Content>> crawlLatestPosts();
}
