package com.blinkflow.flowrun_executor.model.enums;

public enum ActionType {
    SEND_EMAIL("Send an email"),
    SLACK_NOTIFICATION("Send a slack notification"),
    GOOGLE_SHEET("add a row to google sheet"),
    NOTION("Add to Notion doc");
    private final String name;
    private ActionType(String name) { this.name = name; }
    public String getName() { return name; }
}
