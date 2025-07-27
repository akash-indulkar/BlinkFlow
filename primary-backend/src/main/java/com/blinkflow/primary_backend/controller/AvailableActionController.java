package com.blinkflow.primary_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.service.AvailableActionService;

@RestController
@RequestMapping("/actions")
public class AvailableActionController {
	private final AvailableActionService actionService;
	
	@Autowired
	public AvailableActionController(AvailableActionService actionService) {
		this.actionService = actionService;
	}
	
	@GetMapping("/availableactions")
	public ResponseEntity<APIResponse<List<AvailableResponseDTO>>> getActions(){
		List<AvailableResponseDTO> response = actionService.getAvailableActions();
		String message = response.isEmpty() ? "No available actions found" : "Actions fetched successfully";
		return ResponseEntity.ok(new APIResponse<List<AvailableResponseDTO>>(message, response));
	}
}
