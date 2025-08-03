package com.blinkflow.primary_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.ResetOTPVerificationRequest;
import com.blinkflow.primary_backend.dto.SignupOTPVerificationRequest;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.service.EmailOTPService;
import com.blinkflow.primary_backend.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService, EmailOTPService OTPService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<APIResponse<String>> signup(@Valid @RequestBody UserRequestDTO reqUser) {
		String response = userService.createUser(reqUser);
		return ResponseEntity.ok(new APIResponse<String>(response, "Signup verification OTP sent successfully"));
	}

	@PostMapping("/signup/verify")
	public ResponseEntity<APIResponse<UserResponseDTO>> verifySignup(@Valid @RequestBody SignupOTPVerificationRequest OTPreq){
		UserResponseDTO response = userService.verifySignupUser(OTPreq);
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User created successfully", response));
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<UserResponseDTO>> login(@Valid @RequestBody UserRequestDTO reqUser) {
		UserResponseDTO response = userService.authenticateUser(reqUser);
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User logged in successfully", response));
	}

	@PostMapping("/login/forget")
	public ResponseEntity<APIResponse<String>> forgetPassword(@Valid @RequestParam String emailID){
		String response = userService.forgetPassword(emailID);
		return ResponseEntity.ok(new APIResponse<String>("Password reset verification OTP sent successfully", response));
	}
	
	@PostMapping("/login/reset")
	public ResponseEntity<APIResponse<UserResponseDTO>> resetPassword(@Valid @RequestBody ResetOTPVerificationRequest OTPreq){
		UserResponseDTO response = userService.resetPassword(OTPreq);
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("Password reset has done successfully", response));
	}
	
	@GetMapping("/me")
	public ResponseEntity<APIResponse<UserResponseDTO>> getMyInfo() {
		UserResponseDTO response = userService.getMyInformation();
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User information fetched successfully", response));
	}

}
