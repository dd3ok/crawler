package com.blog.crawler.adapter.in.web;

import com.blog.crawler.adapter.in.web.dto.CrawlingRequest;
import com.blog.crawler.adapter.in.web.dto.CrawlingResponse;
import com.blog.crawler.application.port.in.CrawlingUseCase;
import com.blog.crawler.domain.crawler.CrawlingTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/crawling")
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingUseCase crawlingUseCase;

    @PostMapping
    public CompletableFuture<ResponseEntity<CrawlingResponse>> crawl(@RequestBody CrawlingRequest request) {
        CrawlingTarget target = CrawlingTarget.builder()
            .site(request.getSite())
            .boardId(request.getBoardId())
            .url(request.getUrl())
            .pageStart(request.getPageStart())
            .pageEnd(request.getPageEnd())
            .build();

        return crawlingUseCase.crawl(target)
            .thenApply(contents -> ResponseEntity.ok(CrawlingResponse.of(contents)));
    }
}