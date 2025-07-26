package com.blinkflow.flowrun_executor.consumer;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blinkflow.flowrun_executor.dto.FlowRunEventPayload;
import com.blinkflow.flowrun_executor.model.FlowAction;
import com.blinkflow.flowrun_executor.model.FlowRun;
import com.blinkflow.flowrun_executor.model.enums.ActionType;
import com.blinkflow.flowrun_executor.model.enums.FlowRunStatus;
import com.blinkflow.flowrun_executor.repository.FlowRunRepository;
import com.blinkflow.flowrun_executor.service.Email;
import com.blinkflow.flowrun_executor.service.GoogleSheet;
import com.blinkflow.flowrun_executor.service.Notion;
import com.blinkflow.flowrun_executor.service.Slack;

@Component
public class FlowRunConsumer {
	private static final int MAX_RETRIES = 5;

	@Value("${kafka.topic}")
	private String kafkaTopic;

	@Autowired
	private KafkaTemplate<String, FlowRunEventPayload> kafkaTemplate;

	@Autowired
	FlowRunRepository flowRunRepo;

	private Slack slackService;

	@Autowired
	public FlowRunConsumer(Slack slackService) {
	    this.slackService = slackService;
	}

	@Transactional
	@KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
	public void listen(FlowRunEventPayload payload, Acknowledgment acknowledgment) throws Exception {
		Optional<FlowRun> optionalFlowRun = flowRunRepo.findById(payload.getFlowRunID());
		if (optionalFlowRun.isEmpty()) {
			System.out.println("FlowRun not found for ID: " + payload.getFlowRunID());
			acknowledgment.acknowledge();
			return;
		}
		FlowRun flowRun = optionalFlowRun.get();
		List<FlowAction> flowActions = flowRun.getFlow().getFlowActions();
		int stage = payload.getStage();
		if (stage < 0 || stage > flowActions.size()) {
			System.out.println("Invalid stage: " + stage);
			acknowledgment.acknowledge();
			return;
		}
		FlowAction flowAction = flowActions.get(payload.getStage() - 1);
		if (flowAction == null) {
			acknowledgment.acknowledge();
			return;
		}
		try {
			if (flowAction.getActionType().getName().equals(ActionType.SEND_EMAIL.getName()))
				Email.sendEmail(flowRun.getMetadata(), flowAction.getMetadata());
			else if (flowAction.getActionType().getName().equals(ActionType.SLACK_NOTIFICATION.getName()))
				slackService.sendMessageToSlackChannel(flowRun.getMetadata(), flowAction.getMetadata());
			else if (flowAction.getActionType().getName().equals(ActionType.GOOGLE_SHEET.getName()))
				GoogleSheet.addRowInSheet(flowRun.getMetadata(), flowAction.getMetadata());
			else if (flowAction.getActionType().getName().equals(ActionType.NOTION.getName()))
				Notion.insertIntoNotionDoc(flowRun.getMetadata(), flowAction.getMetadata());
		} catch (Exception e) {
			if (payload.getRetryCount() >= MAX_RETRIES) {
				System.out.println("Flow Run with ID: " + payload.getFlowRunID()
						+ " is failed to execute after multiple retries!");
				flowRun.setStatus(FlowRunStatus.FAILED);
				flowRunRepo.save(flowRun);
				acknowledgment.acknowledge();
				return;
			}
			System.out.println("Action failed to execute, again repushing it to queue!");
			payload.setRetryCount(payload.getRetryCount() + 1);
			kafkaTemplate.send(kafkaTopic, payload);
			acknowledgment.acknowledge();
			return;
		}
		if (payload.getStage() < flowActions.size()) {
			FlowRunEventPayload newPayload = FlowRunEventPayload.builder().flowRunID(payload.getFlowRunID())
					.stage(payload.getStage() + 1).build();
			kafkaTemplate.send(kafkaTopic, newPayload);
		} else {
			flowRun.setStatus(FlowRunStatus.SUCCESS);
			flowRunRepo.save(flowRun);
		}
		acknowledgment.acknowledge();
	}
}
