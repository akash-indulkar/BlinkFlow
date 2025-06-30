package com.blinkflow.primary_backend.mapper;

import java.util.stream.Collectors;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.model.Flow;

public class FlowMapper {
	
	public static FlowResponseDTO toResponseDTO(Flow flow){
		return FlowResponseDTO.builder()
				.userID(flow.getUser().getId())
				.flowID(flow.getId())
				.flowTriggerName(flow.getFlowTrigger().getTriggerType().getName())
				.flowActions(flow.getFlowActions().stream().map(action -> action.getActionType().getName()).collect(Collectors.toList()))
				.build();
	}
}
