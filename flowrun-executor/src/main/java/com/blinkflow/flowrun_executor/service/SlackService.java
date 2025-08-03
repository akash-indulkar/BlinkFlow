package com.blinkflow.flowrun_executor.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blinkflow.flowrun_executor.exception.ActionExecutionException;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

@Service
public class SlackService {
	private static final Logger logger = LoggerFactory.getLogger(SlackService.class);
	private final RestTemplate restTemplate;

	@Autowired
	public SlackService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private final String url = "https://slack.com/api/chat.postMessage";

	public void sendMessageToSlackChannel(String prettyFlowRunMetadataMessage, Map<String, Object> slackMetadata) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(slackMetadata.get("OAuthToken").toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("channel", slackMetadata.get("channelID").toString());
		body.put("text", prettyFlowRunMetadataMessage);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody().contains("\"ok\":true")) {
			return;
		} else {
			logger.error("Slack error: " + response.getBody());
			throw new ActionExecutionException("Failed to execute Slack action");
		}
	}
}
