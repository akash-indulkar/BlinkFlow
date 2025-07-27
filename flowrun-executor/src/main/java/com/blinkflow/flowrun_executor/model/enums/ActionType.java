package com.blinkflow.flowrun_executor.model.enums;

public enum ActionType {
    EMAIL("Send an email"),
    SLACK("Send a slack notification"),
    GOOGLE_SHEET("add a row to google sheet"),
    NOTION("Add to Notion doc"),
	ASANA("Add a task to Asana Project"),
	CLICKUP("Create a task in ClickUp List"),
	TELEGRAM("Send a message to Telegram channel"),
	TRELLO("Create card in Trello list");
    private final String name;
    private ActionType(String name) { this.name = name; }
    public String getName() { return name; }
}
