package com.blinkflow.primary_backend.dto;

import java.time.Instant;
import com.blinkflow.primary_backend.model.enums.FlowRunStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class FlowRunResponseDTO {
	@NotNull
	private Long flowRunID;
	@NotBlank
	private String flowName;
	@NotNull
	private Instant triggeredAt;
	@NotEmpty
	@Enumerated(EnumType.STRING)
	private FlowRunStatus status;
	
}
