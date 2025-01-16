package com.blog.crawler.infrastructure.kafka.producer;

import com.blog.crawler.domain.crawler.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.crawler}")
    private String topic;

    public void sendMessage(Content content) {
        try {
            kafkaTemplate.send(topic, content)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully, content: {}", content);  // 전체 content 객체 로깅
                        } else {
                            log.error("Failed to send message", ex);
                        }
                    });
            log.info("Message sent successfully for content ID: {}", content.getId());
        } catch (Exception e) {
            log.error("Error sending message to Kafka: ", e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}