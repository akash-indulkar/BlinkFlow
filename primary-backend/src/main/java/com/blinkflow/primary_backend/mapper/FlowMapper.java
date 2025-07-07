package com.blinkflow.primary_backend.mapper;

import java.util.stream.Collectors;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.model.Flow;

public class FlowMapper {
	
	public static FlowResponseDTO toResponseDTO(Flow flow){
		return FlowResponseDTO.builder()
				.userID(flow.getUser().getId())
				.flowID(flow.getId())
				.name(flow.getName())
				.flowTriggerName(flow.getFlowTrigger().getTriggerType().getName())
				.flowActionNames(flow.getFlowActions().stream().map(action -> action.getActionType().getName()).collect(Collectors.toList()))
				.flowActionImages(flow.getFlowActions().stream().map(action -> action.getActionType().getImage()).collect(Collectors.toList()))
				.build();
	}
}