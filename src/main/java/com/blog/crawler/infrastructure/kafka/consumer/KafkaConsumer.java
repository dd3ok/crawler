package com.blog.crawler.infrastructure.kafka.consumer;

import com.blog.crawler.application.port.out.ContentPersistencePort;
import com.blog.crawler.domain.crawler.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private final ContentPersistencePort contentPersistencePort;

    public KafkaConsumer(ContentPersistencePort contentPersistencePort) {
        this.contentPersistencePort = contentPersistencePort;
    }

    @KafkaListener(
            topics = "${kafka.topic.crawler}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(@Payload Content content) {
        try {
//            log.info("Received content: {}", content.getId());
            contentPersistencePort.save(content);
        } catch (Exception e) {
            log.error("Error processing content: {}", e.getMessage(), e);
        }
    }
}
