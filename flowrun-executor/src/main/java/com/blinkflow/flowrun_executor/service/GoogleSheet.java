package com.blinkflow.flowrun_executor.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GoogleSheet {
	public static void addRowInSheet(Map<String, Object> metadata, Map<String, Object> map) {
		System.out.println("Google Sheet service " + metadata.toString());
	}
}
