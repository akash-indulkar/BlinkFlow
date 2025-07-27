package com.blinkflow.primary_backend.controller;

import java.util.List;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.service.AvailableTriggerService;

@RestController
@RequestMapping("/triggers")
public class AvailableTriggerController {
	private final AvailableTriggerService triggerService;
	
	@Autowired
	public AvailableTriggerController(AvailableTriggerService triggerService) {
		this.triggerService = triggerService;
	}
	
	@GetMapping("/availabletriggers")
	public ResponseEntity<APIResponse<List<AvailableResponseDTO>>> getTrigger(){
		List<AvailableResponseDTO> response = triggerService.getAvailableTrigger();
		String message = response.isEmpty() ? "No available triggers found" : "Available triggers fetched successfully";
		return ResponseEntity.ok(new APIResponse<List<AvailableResponseDTO>>(message, response));
	}
}
