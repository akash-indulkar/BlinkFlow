package com.blinkflow.flowrun_executor.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class Email {
	public static void sendEmail(Map<String, Object> metadata) {
		System.out.println("send email service " + metadata.toString());
	}
}
