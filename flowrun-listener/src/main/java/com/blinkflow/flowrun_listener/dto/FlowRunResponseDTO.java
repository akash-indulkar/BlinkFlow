package com.blinkflow.flowrun_listener.dto;

import com.blinkflow.flowrun_listener.model.enums.FlowRunStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlowRunResponseDTO {
	private int flowRunID;
	private FlowRunStatus status;
	private String message;
}
