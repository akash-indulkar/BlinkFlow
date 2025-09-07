package com.blinkflow.flowrun_listener.util;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class MetadataFormatter {

	private static final Logger logger = LoggerFactory.getLogger(MetadataFormatter.class);
	private final RestTemplate restTemplate;
	private final String GROQ_API_KEY;
	private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
	private final ObjectMapper mapper;

	@Autowired
	public MetadataFormatter(RestTemplate restTemplate, @Value("${groq.api.key}") String apiKey) {
		this.restTemplate = restTemplate;
		this.GROQ_API_KEY = apiKey;
		this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}

	public String metadataToPrettyMessage(Map<String, Object> metadata) {
		String jsonInput;
		try {
			jsonInput = mapper.writeValueAsString(metadata);
		} catch (JsonProcessingException e) {
			logger.warn("Failed to serialize metadata: fallback to toString()", e);
			jsonInput = metadata.toString();
		}

		List<Map<String, Object>> messages = List.of(Map.of("role", "system", "content",
				"You convert webhook metadata into a short, meaningful summary. No headings or context—just the message. E.g. JSON: { \"event\": \"commit\", \"message\": \"Refactored service layer\" } → A commit was made: Refactored service layer."),
				Map.of("role", "user", "content", "Format this JSON:\n\n" + jsonInput));
		Map<String, Object> requestBody = Map.of("model", "openai/gpt-oss-120b", "messages",
				messages);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(GROQ_API_KEY);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(GROQ_URL, HttpMethod.POST, request,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});

		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();

			Object choicesObj = responseBody.get("choices");
			if (choicesObj instanceof List<?> choicesList) {
				List<Map<String, Object>> choices = (List<Map<String, Object>>) choicesObj;
				if (!choices.isEmpty() && choices.get(0) instanceof Map<?, ?> firstChoice) {
					Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
					if (message.get("content") instanceof String) {
						return (String) message.get("content");
					}
				}
			} 
		} else {
			logger.error("Groq API returned error: {}", response.getStatusCode());
		}
		logger.warn("Returning fallback message: Groq API returned non-2xx status or malformed body.");
		return "New event triggered: " + jsonInput;
	}
}
