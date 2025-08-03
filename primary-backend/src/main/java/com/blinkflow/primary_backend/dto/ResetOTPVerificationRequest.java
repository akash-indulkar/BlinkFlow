package com.blinkflow.primary_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResetOTPVerificationRequest {
	@NotBlank
	private String emailID;
	@NotBlank
	private String password;
	@NotBlank
	private String OTP;
}
