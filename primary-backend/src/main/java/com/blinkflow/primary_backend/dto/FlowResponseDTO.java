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
public class FlowResponseDTO {
	@NotNull
	private Long flowID;
	@NotBlank
	private String name;
	@NotNull
	private Long userID;
	@NotNull
	private Long availableTriggerID;
	@NotBlank
	private String flowTriggerName;
	@NotBlank
	private String flowTriggerImage;
	@NotEmpty
	private Map<String, Object> flowTriggerMetadata;
	@NotEmpty
	private List<ActionDTO> flowActions;
}
