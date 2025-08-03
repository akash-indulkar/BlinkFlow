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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.blinkflow.flowrun_executor.exception.ActionExecutionException;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

@Service
public class TrelloService {
	private static final Logger logger = LoggerFactory.getLogger(TrelloService.class);
	private final RestTemplate restTemplate;
	private final String url = "https://api.trello.com/1/cards";
	
	@Autowired
	public TrelloService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void createCardInTrelloList(String prettyFlowRunMetadataMessage, Map<String, Object> trelloMetadata) throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("key", trelloMetadata.get("APIKey").toString());
		params.add("token", trelloMetadata.get("APIToken").toString());
		params.add("idList", trelloMetadata.get("listID").toString()); 
		params.add("name", trelloMetadata.get("cardName").toString()); 
		params.add("desc", prettyFlowRunMetadataMessage);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if(response.getStatusCode().is2xxSuccessful()) {
			return;
		}else {
			logger.error("Trello error: " + response.getBody());
			throw new ActionExecutionException("Failed to execute Trello Action");
		}
	}
}
