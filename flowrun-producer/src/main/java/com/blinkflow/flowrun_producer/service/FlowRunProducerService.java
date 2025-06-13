package com.blinkflow.flowrun_producer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blinkflow.flowrun_producer.dto.FlowRunEventPayload;
import com.blinkflow.flowrun_producer.model.FlowRun;
import com.blinkflow.flowrun_producer.model.FlowRunOutBox;
import com.blinkflow.flowrun_producer.model.enums.FlowRunStatus;
import com.blinkflow.flowrun_producer.repository.FlowRunOutBoxRepository;
import com.blinkflow.flowrun_producer.repository.FlowRunRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FlowRunProducerService {
	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	private FlowRunOutBoxRepository flowRunOutBoxRepo;
	
	@Autowired
	private FlowRunRepository flowRunRepository;
	
	@Scheduled(fixedDelay = 5000)
	@Transactional
	public void pollAndPublish() throws JsonProcessingException {
		List<FlowRunOutBox> flowRunOutBoxs = flowRunOutBoxRepo.findTop10ByOrderByIdAsc();
		if(flowRunOutBoxs.isEmpty()) return;
		kafkaTemplate.executeInTransaction(tx -> {
			List<FlowRun> flowRuns = new ArrayList<FlowRun>();
			for(FlowRunOutBox flowRunOutBox : flowRunOutBoxs) {
				FlowRunEventPayload payload = FlowRunEventPayload.builder().flowRunID(flowRunOutBox.getFlowRun().getId()).Stage(0).build();
				tx.send(kafkaTopic, payload);
				flowRunOutBox.getFlowRun().setStatus(FlowRunStatus.RUNNING);
				flowRuns.add(flowRunOutBox.getFlowRun());
			}
			flowRunRepository.saveAll(flowRuns);
			flowRunOutBoxRepo.deleteAll(flowRunOutBoxs);
			return true;
		});
	}		
}
