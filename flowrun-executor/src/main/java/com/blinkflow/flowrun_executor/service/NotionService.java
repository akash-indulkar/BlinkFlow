package com.blinkflow.flowrun_executor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

@Service
public class NotionService {
	private final RestTemplate restTemplate;
	
	@Autowired
	public NotionService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void insertIntoNotionDoc(Map<String, Object> flowRunMetadata, Map<String, Object> notionMetadata) throws Exception {
		final String url = "https://api.notion.com/v1/blocks/"+ notionMetadata.get("pageID").toString() +"/children";
		HttpHeaders headers =  new HttpHeaders();
		headers.setBearerAuth(notionMetadata.get("notionSecret").toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Notion-version", "2022-06-28");
		
		String serializableMetadataMessage = MetadataFormatter.toPrettyJson(flowRunMetadata);
		
		Map<String, Object> textContent = new HashMap<String, Object>();
		textContent.put("content", serializableMetadataMessage);
		
		Map<String, Object> text = new HashMap<String, Object>();
		text.put("type", "text");
		text.put("text", textContent);
		
		List<Map<String, Object>> richText = new ArrayList<Map<String, Object>>();
		richText.add(text);
		
		Map<String, Object> paragraph = new HashMap<String, Object>();
		paragraph.put("rich_text", richText);
		
		Map<String, Object> childBlock = new HashMap<String, Object>();
		childBlock.put("object", "block");
		childBlock.put("type", "paragraph");
		childBlock.put("paragraph", paragraph);
		
		List<Map<String, Object>> childrenList = new ArrayList<Map<String,Object>>();
		childrenList.add(childBlock);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("children", childrenList);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String,Object>>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, request, String.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			return;
		} else {
			System.err.println("Notion error: " + response.getBody());
			throw new Exception("Failed to execute Notion action");
		}
	}
}
