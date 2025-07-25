package com.blinkflow.flowrun_executor.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Slack {

	private final RestTemplate restTemplate;

	@Autowired
	public Slack(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private final String url = "https://slack.com/api/chat.postMessage";

	public void sendMessageToSlackChannel(Map<String, Object> flowRunMetadata, Map<String, Object> slackMetadata) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(flowRunMetadata.get("OAuthToken").toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("channel", flowRunMetadata.get("channelID"));
		body.put("message", slackMetadata);
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if (response.getStatusCode().is2xxSuccessful() && response.getBody().contains("\"ok\":true")) {
			return;
		} else {
			System.err.println("Slack error: " + response.getBody());
			throw new Exception("Failed to execute Slack action");
		}
	}
}
