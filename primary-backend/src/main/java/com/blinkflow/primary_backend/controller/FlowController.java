package com.blinkflow.primary_backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blinkflow.primary_backend.dto.APIResponse;
import com.blinkflow.primary_backend.dto.FlowRequestDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.service.FlowService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/flow")
public class FlowController {
	private final FlowService flowService; 
	
	@Autowired
	public FlowController(FlowService flowService) {
		this.flowService = flowService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<APIResponse<FlowResponseDTO>> createFlow(@Valid @RequestBody FlowRequestDTO flowReq){
		FlowResponseDTO response = flowService.createFlow(flowReq);
		return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<FlowResponseDTO>("Flow created successfully", response));
	}
	
	@DeleteMapping("/delete/{flowID}")
	public ResponseEntity<APIResponse<Long>> deleteFlow(@PathVariable Long flowID){
		Long response = flowService.deleteFlowByID(flowID);
		return ResponseEntity.status(HttpStatus.OK).body(new APIResponse<Long>("Flow deleted successfully", response));
	}
	
	@PutMapping("/update/{flowID}")
	public ResponseEntity<APIResponse<FlowResponseDTO>> updateFlow(@PathVariable Long flowID, @Valid @RequestBody FlowRequestDTO flowReq){
		FlowResponseDTO response = flowService.updateFlowByID(flowID, flowReq);
		return ResponseEntity.ok(new APIResponse<FlowResponseDTO>("Flow updated successfully", response));
	}
	
	@GetMapping("/")
	public ResponseEntity<APIResponse<List<FlowResponseDTO>>> getAllFlows(){
		List<FlowResponseDTO> response = flowService.getAllFlows();
		String message = response.isEmpty() ? "No flows found" : "Flows fetched successfully";
		return ResponseEntity.ok(new APIResponse<List<FlowResponseDTO>>(message, response));
	}
	
	@GetMapping("/{flowID}")
	public ResponseEntity<APIResponse<FlowResponseDTO>> getFlow(@PathVariable Long flowID){
		FlowResponseDTO response = flowService.getFlowByID(flowID);
		return ResponseEntity.ok(new APIResponse<FlowResponseDTO>("Flow fetched successfully", response));
	}
}
