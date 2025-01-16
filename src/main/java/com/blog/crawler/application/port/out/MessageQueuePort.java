package com.blog.crawler.application.port.out;

import com.blog.crawler.domain.crawler.Content;

public interface MessageQueuePort {
    void sendContent(Content content);
}