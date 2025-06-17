package com.blinkflow.flowrun_executor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlowRunEventPayload {
	private Long flowRunID;
	private Integer stage;
	private Integer retryCount;
}
