package com.blinkflow.primary_backend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.FlowRunResponseDTO;
import com.blinkflow.primary_backend.exception.EntityNotFound;
import com.blinkflow.primary_backend.model.FlowRun;
import com.blinkflow.primary_backend.repository.FlowRunRepository;

@Service
public class FlowRunService {

	private final FlowRunRepository flowRunRepo;
	
	@Autowired
	public FlowRunService(FlowRunRepository flowRunRepo) {
		this.flowRunRepo = flowRunRepo;
	}
	
	public List<FlowRunResponseDTO> getAllFlows(Long flowID) {
		List<FlowRun> flowRuns = Optional.ofNullable(flowRunRepo.findByFlowId(flowID))
									.orElseThrow(() -> new EntityNotFound("FlowRuns Not Found"));
		return flowRuns.stream().map(flowRun -> FlowRunResponseDTO.builder()
				.flowRunID(flowRun.getId())
				.flowName(flowRun.getFlow().getName())
				.triggeredAt(flowRun.getTriggeredAt())
				.status(flowRun.getStatus())
				.build()
				)
				.toList();
	}
}
