package com.blog.crawler.infrastructure.kafka;

import com.blog.crawler.application.port.out.MessageQueuePort;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.infrastructure.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageAdapter implements MessageQueuePort {
    private final KafkaProducer kafkaProducer;

    @Override
    public void sendContent(Content content) {
        kafkaProducer.sendMessage(content);
    }
}