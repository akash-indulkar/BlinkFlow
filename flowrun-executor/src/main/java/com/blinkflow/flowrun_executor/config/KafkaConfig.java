package com.blinkflow.flowrun_executor.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import com.blinkflow.flowrun_executor.dto.FlowRunEventPayload;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${kafka.group-id}")
	private String groupID;

	@Value("${kafka.transactional.id}")
	private String transactionalID;

	@Bean
	public ConsumerFactory<String, FlowRunEventPayload> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		JsonDeserializer<FlowRunEventPayload> deserializer = new JsonDeserializer<>(FlowRunEventPayload.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("com.blinkflow.flowrun_producer.dto,com.blinkflow.flowrun_executor.dto");
		deserializer.setUseTypeMapperForKey(true);
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
		config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, FlowRunEventPayload> kafkaListenerContainerFactory() {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, FlowRunEventPayload>();
		factory.setConsumerFactory(consumerFactory());
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

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
