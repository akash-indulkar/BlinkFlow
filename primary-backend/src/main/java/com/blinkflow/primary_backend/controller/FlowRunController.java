package com.blinkflow.primary_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.FlowRunResponseDTO;
import com.blinkflow.primary_backend.service.FlowRunService;

@RestController
@RequestMapping("/flowrun")
public class FlowRunController {
	private final FlowRunService flowRunService; 
	
	@Autowired
	public FlowRunController(FlowRunService flowRunService) {
		this.flowRunService = flowRunService;
	}
	
	@GetMapping("/logs/{flowID}")
	public ResponseEntity<APIResponse<List<FlowRunResponseDTO>>> getFlowRunLogs(@PathVariable Long flowID){
		List<FlowRunResponseDTO> response = flowRunService.getAllFlows(flowID);
		String message = response.isEmpty() ? "No flows found" : "FlowRuns fetched successfully";
		return ResponseEntity.ok(new APIResponse<List<FlowRunResponseDTO>>(message, response));
	
	}
}
