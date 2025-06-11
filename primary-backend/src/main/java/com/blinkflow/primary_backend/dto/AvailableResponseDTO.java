package com.blinkflow.primary_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AvailableResponseDTO {
	@NotNull
	private int id;
	@NotBlank
	private String name;
	@NotBlank
	private String image;
}
