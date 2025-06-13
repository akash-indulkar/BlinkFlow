package com.blinkflow.primary_backend.controller;

import java.util.List;
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
import com.blinkflow.primary_backend.dto.FlowRequestDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.service.FlowService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/flow")
public class FlowController {
	@Autowired
	private FlowService flowService; 
	
	@PostMapping("/create")
	public ResponseEntity<?> createFlow(@Valid @RequestBody FlowRequestDTO flowReq){
		Optional<FlowResponseDTO> response = flowService.createFlow(flowReq);
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to invalid data");
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllFlows(){
		Optional<List<FlowResponseDTO>> response = flowService.getAllFlows();
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch your flows");
	}
	
	@GetMapping("/{flowID}")
	public ResponseEntity<?> getFlow(@PathVariable Long flowID){
		Optional<FlowResponseDTO> response = flowService.getFlowByID(flowID);
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(response.get());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch your flows");
	}
}
