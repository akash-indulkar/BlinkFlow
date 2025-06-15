package com.blinkflow.flowrun_executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class FlowrunExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowrunExecutorApplication.class, args);
	}

}
