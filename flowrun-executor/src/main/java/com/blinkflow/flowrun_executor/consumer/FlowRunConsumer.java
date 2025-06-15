package com.blinkflow.flowrun_executor.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FlowRunConsumer {

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void listen(String message) {
        System.out.println("🔔 Received message: " + message);
    }
}
