package com.blinkflow.flowrun_executor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.blinkflow.flowrun_executor.exception.ActionExecutionException;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AsanaService {
	private static final Logger logger = LoggerFactory.getLogger(AsanaService.class);
	private final String url = "https://app.asana.com/api/1.0/tasks";
	private final RestTemplate restTemplate;
	
	@Autowired
	public AsanaService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void addTaskToAsanaProject(Map<String, Object> flowRunMetadata, Map<String, Object> asanaMetadata) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(asanaMetadata.get("personalAccessToken").toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", asanaMetadata.get("taskName").toString());
		
		String serializableMessage = MetadataFormatter.toPrettyJson(flowRunMetadata);
		data.put("notes", serializableMessage);
		
		List<String> projects = new ArrayList<String>();
		projects.add(asanaMetadata.get("projectID").toString());
		data.put("projects",projects);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("data", data);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String,Object>>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			return;
		}else {
			logger.error("Asana error: " + response.getBody());
			throw new ActionExecutionException("Failed to execute Asana action");
		}
	}
}
