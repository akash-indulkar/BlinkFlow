package com.blinkflow.primary_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRequestDTO {
	@Email
	@NotBlank(message = "Email is mandatory")
	private String email;
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "Password is mandatory")
	private String password;
}
