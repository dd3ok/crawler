package com.blog.crawler.common.exception;

public class CrawlerException extends RuntimeException {
    public CrawlerException(String message) {
        super(message);
    }

    public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
