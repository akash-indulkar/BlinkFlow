package com.blinkflow.primary_backend.mapper;

import java.util.stream.Collectors;

import com.blinkflow.primary_backend.dto.ActionDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.model.Flow;

public class FlowMapper {
	
	public static FlowResponseDTO toResponseDTO(Flow flow){
		return FlowResponseDTO.builder()
				.flowID(flow.getId())
				.name(flow.getName())
				.userID(flow.getUser().getId())
				.availableTriggerID(flow.getFlowTrigger().getTriggerType().getId())
				.flowTriggerName(flow.getFlowTrigger().getTriggerType().getName())
				.flowTriggerImage(flow.getFlowTrigger().getTriggerType().getImage())
				.flowTriggerMetadata(flow.getFlowTrigger().getMetadata())
				.flowActions(
						flow.getFlowActions().stream()
							.map(action -> ActionDTO.builder()
									.availableActionID(action.getActionType().getId())
									.flowActionName(action.getActionType().getName())
									.flowActionImage(action.getActionType().getImage())
									.metadata(action.getMetadata())
									.sortingOrder(action.getSortingOrder())
									.build()
							).toList())
				.build();
	}
}