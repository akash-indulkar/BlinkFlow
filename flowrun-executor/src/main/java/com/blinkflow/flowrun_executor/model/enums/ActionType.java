	package com.blinkflow.flowrun_executor.model.enums;

public enum ActionType {
    EMAIL("Send an Email"),
    SLACK("Send Slack Message"),
    NOTION("Append to Notion Page"),
	ASANA("Create Asana Task"),
	CLICKUP("Create ClickUp Task"),
	TELEGRAM("Send Telegram Channel Message"),
	TRELLO("Create Trello Card");
    private final String name;
    private ActionType(String name) { this.name = name; }
    public String getName() { return name; }
}
