package com.blog.crawler.adapter.in.web.dto;

import com.blog.crawler.domain.crawler.Site;
import lombok.Getter;

@Getter
public class CrawlingRequest {
    private Site site;
    private String boardId;
    private String url;
    private int pageStart;
    private int pageEnd;
}