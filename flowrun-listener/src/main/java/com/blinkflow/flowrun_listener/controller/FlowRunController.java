package com.blinkflow.flowrun_listener.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.flowrun_listener.dto.FlowRunResponseDTO;
import com.blinkflow.flowrun_listener.service.FlowRunService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/flowrun")
public class FlowRunController {
	private final FlowRunService flowRunService;

	@Autowired
	public FlowRunController(FlowRunService flowRunService) {
		this.flowRunService = flowRunService;
	}
	
	@PostMapping("/initiate/{userID}/{flowID}")
	public ResponseEntity<FlowRunResponseDTO> initiateFlowRun(@PathVariable Long userID, @PathVariable Long flowID, @RequestBody Map<String, Object> requestBody, HttpServletRequest request) throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		FlowRunResponseDTO response = flowRunService.intiateFlowRun(userID, flowID, requestBody, request);
		return ResponseEntity.ok(response);
	}
}
