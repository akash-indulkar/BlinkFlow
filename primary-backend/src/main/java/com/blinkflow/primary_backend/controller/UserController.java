package com.blinkflow.primary_backend.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequestDTO reqUser){
		Optional<UserResponseDTO> response =  userService.createUser(reqUser);
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
		return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists. Please log in.");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO reqUser){
		Optional<UserResponseDTO> response =  userService.authenticateUser(reqUser);
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials, Please try again.");
		}
	
	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(){
		Optional<UserResponseDTO> response =  userService.getMyInformation();
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found, Please try again.");
	}
	
	//forget password route need to be added after email integration
}
