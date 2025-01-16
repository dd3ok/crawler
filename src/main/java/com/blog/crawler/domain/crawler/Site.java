package com.blog.crawler.domain.crawler;

public enum Site {
    NATE_NEWS("네이트뉴스");

    private final String description;

    Site(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}