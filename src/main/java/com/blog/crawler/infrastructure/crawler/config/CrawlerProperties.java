package com.blog.crawler.infrastructure.crawler.config;

import com.blog.crawler.domain.crawler.Site;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "crawler")
@Configuration
public class CrawlerProperties {
    private Retry retry;
    private Wait wait;
    private List<Target> targets;
    private Schedule schedule;

    @Getter
    @Setter
    public static class Target {
        private Site site;
        private String boardId;
        private String url;
    }

    @Getter
    @Setter
    public static class Schedule {
        private int interval;
    }

    @Getter
    @Setter
    public static class Retry {
        private int maxAttempts;
        private long delayMs;
    }

    @Getter
    @Setter
    public static class Wait {
        private long defaultTimeMs;
    }
}
