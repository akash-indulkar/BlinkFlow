package com.blinkflow.primary_backend.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlowRequestDTO {
	@NotNull
	private Long userID;
	private String name;
	@NotNull
	private Long availableTriggerID;
	@NotEmpty
	private Map<String, Object> triggerMetadata;
	@NotEmpty
	private List<ActionDTO> flowActions;
}
