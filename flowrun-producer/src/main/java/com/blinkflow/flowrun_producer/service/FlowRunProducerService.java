package com.blinkflow.flowrun_producer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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

@Service
public class FlowRunProducerService {
	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	@Autowired
	private KafkaTemplate<String, FlowRunEventPayload> kafkaTemplate;
	
	@Autowired
	private FlowRunOutBoxRepository flowRunOutBoxRepo;
	
	@Autowired
	private FlowRunRepository flowRunRepository;
	
	@Scheduled(fixedDelay = 500)
	@Transactional
	public void pollAndPublish() throws InterruptedException, ExecutionException {
		List<FlowRunOutBox> flowRunOutBoxs = flowRunOutBoxRepo.findTop10ByOrderByIdAsc();
		if(flowRunOutBoxs.isEmpty()) return;
		List<FlowRun> flowRuns = new ArrayList<FlowRun>();
		for(FlowRunOutBox flowRunOutBox : flowRunOutBoxs) {
			FlowRunEventPayload payload = FlowRunEventPayload.builder().flowRunID(flowRunOutBox.getFlowRunID()).stage(1).retryCount(0).build();
			kafkaTemplate.send(kafkaTopic, payload).get();
			FlowRun flowRun = flowRunRepository.findById(flowRunOutBox.getFlowRunID()).get();
			flowRun.setStatus(FlowRunStatus.RUNNING);
			flowRuns.add(flowRun);
		}
		flowRunRepository.saveAll(flowRuns);
		flowRunOutBoxRepo.deleteAll(flowRunOutBoxs);
	}		
}
