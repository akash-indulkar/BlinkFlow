package com.blinkflow.primary_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blinkflow.primary_backend.dto.ActionDTO;
import com.blinkflow.primary_backend.dto.FlowRequestDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.model.AvailableAction;
import com.blinkflow.primary_backend.model.AvailableTrigger;
import com.blinkflow.primary_backend.model.Flow;
import com.blinkflow.primary_backend.model.FlowAction;
import com.blinkflow.primary_backend.model.FlowTrigger;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.model.UserPrincipal;
import com.blinkflow.primary_backend.repositary.AvailableActionRepository;
import com.blinkflow.primary_backend.repositary.AvailableTriggerRepositary;
import com.blinkflow.primary_backend.repositary.FlowActionRepositary;
import com.blinkflow.primary_backend.repositary.FlowRepositary;
import com.blinkflow.primary_backend.repositary.FlowTriggerRepositary;
import com.blinkflow.primary_backend.repositary.UserRepositary;

import jakarta.validation.Valid;

@Service
public class FlowService {
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	private FlowRepositary flowRepo;
	
	@Autowired
	private AvailableTriggerRepositary availableTriggerRepo;
	
	@Autowired
	private AvailableActionRepository availableActionRepo;
	
	public Optional<FlowResponseDTO> createFlow(@Valid FlowRequestDTO flowReq) {
		Optional<User> user = userRepo.findById(flowReq.getUserID());
		if(user.isEmpty()) return Optional.empty();
		
		Flow flow = Flow.builder()
					.user(user.get())
					.build();
		
		Optional<AvailableTrigger> availableTriggerType = availableTriggerRepo.findById(flowReq.getAvailableTriggerID());
		if(availableTriggerType.isEmpty()) return Optional.empty();
		FlowTrigger trigger = FlowTrigger.builder()
								.metadata(flowReq.getTriggerMetadata())
								.triggerType(availableTriggerType.get())
								.flow(flow)
								.build();
		
		List<FlowAction> actions = new ArrayList<FlowAction>();
		for (ActionDTO action: flowReq.getFlowActions()) {
			Optional<AvailableAction> availableActionType = availableActionRepo.findById(action.getAvailableActionID());
			if(availableActionType.isEmpty()) return Optional.empty();
			FlowAction flowAction = FlowAction.builder()
									.metadata(action.getMetadata())
									.flow(flow)
									.actionType(availableActionType.get())
									.sortingOrder(action.getSortingOrder())
									.build();
			actions.add(flowAction);
		}
		
		flow.setFlowTrigger(trigger);
		flow.setFlowActions(actions);
		Flow savedFlow = flowRepo.save(flow);
		
		FlowResponseDTO response = FlowResponseDTO.builder()
									.flowID(savedFlow.getId())
									.flowTriggerName(savedFlow.getFlowTrigger().getTriggerType().getName())
									.flowActions(savedFlow.getFlowActions().stream().map(action -> action.getActionType().getName()).collect(Collectors.toList()))
									.build();
		return Optional.of(response);
	}

	public Optional<List<FlowResponseDTO>> getAllFlows() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		List<Flow> flows = flowRepo.findAllByUserId(userPrincipal.getUser().getId());
		if(flows == null) return Optional.empty();
		List<FlowResponseDTO> flowResponses = new ArrayList<FlowResponseDTO>();
		for(Flow flow : flows) {
			FlowResponseDTO response = FlowResponseDTO.builder()
					.flowID(flow.getId())
					.flowTriggerName(flow.getFlowTrigger().getTriggerType().getName())
					.flowActions(flow.getFlowActions().stream().map(action -> action.getActionType().getName()).collect(Collectors.toList()))
					.build();
			flowResponses.add(response);
		}
		
		return Optional.of(flowResponses);
	}

	public Optional<FlowResponseDTO> getFlowByID(int flowID) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		Flow flow = flowRepo.findByUserIdAndId(userPrincipal.getUser().getId(), flowID);
		if(flow == null) return Optional.empty();
		FlowResponseDTO response = FlowResponseDTO.builder()
				.flowID(flow.getId())
				.flowTriggerName(flow.getFlowTrigger().getTriggerType().getName())
				.flowActions(flow.getFlowActions().stream().map(action -> action.getActionType().getName()).collect(Collectors.toList()))
				.build();
		return Optional.of(response);
	}
}
