package com.blog.crawler.application.port.out;

import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.CrawlingTarget;

import java.util.List;

public interface CrawlingPort {
    List<Content> extractContents(CrawlingTarget target, int page);
}
