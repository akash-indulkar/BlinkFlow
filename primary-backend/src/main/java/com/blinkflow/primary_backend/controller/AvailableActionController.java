package com.blinkflow.primary_backend.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.service.AvailableActionService;

@RestController
@RequestMapping("/actions")
public class AvailableActionController {
	@Autowired
	private AvailableActionService actionService;
	
	@GetMapping("/availableactions")
	public ResponseEntity<?> getActions(){
		Optional<List<AvailableResponseDTO>> response = actionService.getAvailableActions();
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while fetching available actions");
	}
}
