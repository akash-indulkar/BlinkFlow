package com.blinkflow.flowrun_producer.dto;

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
	private Integer Stage;
}
