package com.blog.crawler.domain.crawler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CrawlingTarget {
    private String id;
    private Site site;
    private String boardId;
    private String url;
    private int pageStart;
    private int pageEnd;
}