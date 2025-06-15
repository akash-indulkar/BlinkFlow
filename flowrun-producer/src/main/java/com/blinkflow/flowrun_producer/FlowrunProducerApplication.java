package com.blinkflow.flowrun_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlowrunProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowrunProducerApplication.class, args);
	}

}
