package com.blinkflow.primary_backend.dto;

import java.util.Map;
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
public class ActionDTO {
	@NotNull
	private Long availableActionID;
	@NotEmpty
	private Map<String, Object> metadata;
	@NotNull
	private Integer sortingOrder;
}
