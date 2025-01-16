package com.blog.crawler.application.port.out;

import com.blog.crawler.domain.crawler.Content;

public interface ContentPersistencePort {
    void save(Content content);
    boolean existsById(String id);
}