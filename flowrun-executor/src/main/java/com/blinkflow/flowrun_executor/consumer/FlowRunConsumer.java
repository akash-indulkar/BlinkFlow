package com.blinkflow.flowrun_executor.consumer;

import java.util.concurrent.Flow;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.blinkflow.flowrun_executor.dto.FlowRunEventPayload;

@Component
public class FlowRunConsumer {

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void listen(FlowRunEventPayload payload) {
        System.out.println("🔔 Received message: " + payload);
    }
}
