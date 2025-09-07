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

@Service
public class ClickUpService {
	private final RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ClickUpService.class);

	
	@Autowired
	public ClickUpService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void createTaskInClickUpList(String prettyFlowRunMetadataMessage, Map<String, Object> clickUpMetadata) throws Exception {
		final String url = "https://api.clickup.com/api/v2/list/" + clickUpMetadata.get("listID").toString() + "/task";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", clickUpMetadata.get("APIToken").toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("name", clickUpMetadata.get("taskName"));
		body.put("description", prettyFlowRunMetadataMessage);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String,Object>>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if(response.getStatusCode().is2xxSuccessful()) {
			return;
		}else {
			logger.error("ClickUp error : " + response.getBody());
			throw new ActionExecutionException("Failed to execute ClickUp action");
		}
	}
}
