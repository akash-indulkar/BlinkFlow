package com.blinkflow.flowrun_executor.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class Notion {
	public static void insertIntoNotionDoc(Map<String, Object> metadata, Map<String, Object> map) {
		System.out.println("Notion doc service " + metadata.toString());
	}
}
