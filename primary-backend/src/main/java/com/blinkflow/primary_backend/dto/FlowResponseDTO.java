package com.blinkflow.primary_backend.dto;

import java.util.List;
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
	@NotNull
	private Long userID;
	@NotBlank
	private String flowTriggerName;
	@NotEmpty
	private List<String> flowActions;
}
