package com.blinkflow.flowrun_executor.service;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.blinkflow.flowrun_executor.exception.ActionExecutionException;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

@Service
public class TrelloService {
	private static final Logger logger = LoggerFactory.getLogger(TrelloService.class);
	private final RestTemplate restTemplate;
	
	@Autowired
	public TrelloService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void createCardInTrelloList(Map<String, Object> flowRunMetadata, Map<String, Object> trelloMetadata) throws Exception {
		String serializableMetadataMessage = MetadataFormatter.toPrettyJson(flowRunMetadata);
		
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("https://api.trello.com/1/cards")
				.queryParam("key", trelloMetadata.get("APIKey").toString())
				.queryParam("token", trelloMetadata.get("APIToken").toString())
				.queryParam("idList", trelloMetadata.get("listID").toString())
				.queryParam("name", trelloMetadata.get("cardName").toString())
				.queryParam("desc", serializableMetadataMessage);
		
		String url = builder.toUriString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			return;
		}else {
			logger.error("Trello error: " + response.getBody());
			throw new ActionExecutionException("Failed to execute Trello Action");
		}
	}
}
