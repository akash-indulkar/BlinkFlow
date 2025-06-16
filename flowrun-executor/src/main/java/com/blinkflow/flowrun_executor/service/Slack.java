package com.blinkflow.flowrun_executor.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class Slack {
	public static void sendMessageToSlackChannel(Map<String, Object> metadata) {
		System.out.println("Slack Service " + metadata.toString());
	}
}
