package com.blog.crawler.adapter.in.web.dto;

import com.blog.crawler.domain.crawler.Content;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CrawlingResponse {
    private int totalCount;
    private List<Content> contents;
    
    public static CrawlingResponse of(List<Content> contents) {
        return CrawlingResponse.builder()
            .totalCount(contents.size())
            .contents(contents)
            .build();
    }
}