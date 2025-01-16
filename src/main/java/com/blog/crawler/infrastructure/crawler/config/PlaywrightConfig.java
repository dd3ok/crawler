package com.blog.crawler.infrastructure.crawler.config;

import com.microsoft.playwright.Playwright;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {
    @Bean(destroyMethod = "close")
    public Playwright playwright() {
        return Playwright.create();
    }
}