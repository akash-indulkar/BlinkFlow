package com.blinkflow.primary_backend.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<APIResponse<UserResponseDTO>> signup(@Valid @RequestBody UserRequestDTO reqUser) {
		UserResponseDTO response = userService.createUser(reqUser);
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User created successfully", response));
	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse<UserResponseDTO>> login(@Valid @RequestBody UserRequestDTO reqUser) {
		UserResponseDTO response = userService.authenticateUser(reqUser);
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User logged in successfully", response));
	}

	@GetMapping("/me")
	public ResponseEntity<APIResponse<UserResponseDTO>> getMyInfo() {
		UserResponseDTO response = userService.getMyInformation();
		return ResponseEntity.ok(new APIResponse<UserResponseDTO>("User information fetched successfully", response));
	}

	// forget password route need to be added after email integration
}
