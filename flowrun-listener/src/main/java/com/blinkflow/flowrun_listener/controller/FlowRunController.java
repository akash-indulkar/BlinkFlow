package com.blinkflow.flowrun_listener.controller;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.flowrun_listener.dto.FlowRunResponseDTO;
import com.blinkflow.flowrun_listener.service.FlowRunService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/flowrun")
public class FlowRunController {
	@Autowired
	private FlowRunService flowRunService;
	
	@PostMapping("/initiate/{userID}/{flowID}")
	public ResponseEntity<?> initiateFlowRun(@PathVariable int userID, @PathVariable int flowID, @RequestBody Map<String, Object> requestBody, HttpServletRequest request) throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		Optional<FlowRunResponseDTO> response = flowRunService.intiateFlowRun(userID, flowID, requestBody, request);
		if(response.isPresent()) return ResponseEntity.status(HttpStatus.ACCEPTED).body(response.get());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	}
}
