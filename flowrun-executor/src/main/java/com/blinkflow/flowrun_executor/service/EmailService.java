package com.blinkflow.flowrun_executor.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public void sendEmail(Map<String, Object> metadata, Map<String, Object> map) throws Exception {
		System.out.println("send email service " + metadata.toString());
	}
}
