package com.blinkflow.flowrun_executor.consumer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.blinkflow.flowrun_executor.dto.FlowRunEventPayload;
import com.blinkflow.flowrun_executor.model.FlowAction;
import com.blinkflow.flowrun_executor.model.FlowRun;
import com.blinkflow.flowrun_executor.repository.FlowRunRepository;
import com.blinkflow.flowrun_executor.service.Email;
import com.blinkflow.flowrun_executor.service.GoogleSheet;
import com.blinkflow.flowrun_executor.service.Notion;
import com.blinkflow.flowrun_executor.service.Slack;

@Component
public class FlowRunConsumer {
	
	@Autowired
	FlowRunRepository flowRunRepo;
	
    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void listen(FlowRunEventPayload payload) throws Exception {
    	FlowRun flowRun = flowRunRepo.findById(payload.getFlowRunID()).get();
    	List<FlowAction> flowActions = flowRun.getFlow().getFlowActions();
    	for(FlowAction flowAction : flowActions) {
    		if(flowAction.getActionType().getName().equals("Send an email")) {
    			Email.sendEmail(flowAction.getMetadata());
    		}else if(flowAction.getActionType().getName().equals("Send a slack notification")) {
			    Slack.sendMessageToSlackChannel(flowAction.getMetadata());	
			}else if(flowAction.getActionType().getName().equals("add a row to google sheet")) {
				GoogleSheet.addRowInSheet(flowAction.getMetadata());
			}else if(flowAction.getActionType().getName().equals("Add to Notion doc")) {
				Notion.insertIntoNotionDoc(flowAction.getMetadata());
			}else {
				throw new Exception("Action not available");
			}
    	}
    	
    }
}
