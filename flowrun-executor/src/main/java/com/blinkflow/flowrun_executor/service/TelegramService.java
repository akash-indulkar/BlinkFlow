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
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

@Service
public class TelegramService {
	private static final Logger logger = LoggerFactory.getLogger(TelegramService.class);
	private final RestTemplate restTemplate;
	
	@Autowired
	public TelegramService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void sendMessageToTelegramChannel(Map<String, Object> flowRunMetadata, Map<String, Object> telegramMetadata) throws Exception {
		final String url = "https://api.telegram.org/"+ telegramMetadata.get("botToken") + "/sendMessage";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object>	body = new HashMap<String, Object>();
		body.put("chat_id", telegramMetadata.get("channelUsername").toString());
		
		String serializableMetadataMessage = MetadataFormatter.toPrettyJson(flowRunMetadata);
		body.put("text", serializableMetadataMessage);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String,Object>>(body, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			return;
		}else {
			logger.error("Telegram error: "+ response.getBody());
			throw new Exception("Failed to execute Telegram action");
		}
	}
}
