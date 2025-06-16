package com.blinkflow.flowrun_producer.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.blinkflow.flowrun_producer.dto.FlowRunEventPayload;

@Configuration
public class KafkaConfig {
	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	 @Value("${kafka.transactional.id}")
	 private String transactionalID;
	
    @Bean
    public ProducerFactory<String, FlowRunEventPayload> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalID);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, FlowRunEventPayload> kafkaTemplate() {
        KafkaTemplate<String, FlowRunEventPayload> kafkaTemplate = new KafkaTemplate<>(producerFactory());
    	kafkaTemplate.setTransactionIdPrefix("tx-");
    	return kafkaTemplate;
    }
}
