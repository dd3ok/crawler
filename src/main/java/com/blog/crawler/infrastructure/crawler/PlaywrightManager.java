package com.blog.crawler.infrastructure.crawler;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlaywrightManager {
    private final Playwright playwright;
    private final Browser browser;
    private final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();

    public PlaywrightManager(Playwright playwright) {
        this.playwright = playwright;
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true));
    }

    public Page createPage() {
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .setViewportSize(1920, 1080)
                .setIgnoreHTTPSErrors(true));

        browserContext.set(context);
        return context.newPage();
    }

    public void closePage(Page page) {
        if (page != null) {
            try {
                page.close();
            } catch (Exception e) {
                log.warn("Error closing page: {}", e.getMessage());
            }
        }

        BrowserContext context = browserContext.get();
        if (context != null) {
            try {
                context.close();
                browserContext.remove();
            } catch (Exception e) {
                log.warn("Error closing context: {}", e.getMessage());
            }
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        } catch (Exception e) {
            log.error("Error during Playwright cleanup", e);
        }
    }
}
