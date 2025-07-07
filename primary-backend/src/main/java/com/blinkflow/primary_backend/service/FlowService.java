package com.blinkflow.primary_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.ActionDTO;
import com.blinkflow.primary_backend.dto.FlowRequestDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.mapper.FlowMapper;
import com.blinkflow.primary_backend.model.AvailableAction;
import com.blinkflow.primary_backend.model.AvailableTrigger;
import com.blinkflow.primary_backend.model.Flow;
import com.blinkflow.primary_backend.model.FlowAction;
import com.blinkflow.primary_backend.model.FlowTrigger;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.model.UserPrincipal;
import com.blinkflow.primary_backend.repository.AvailableActionRepository;
import com.blinkflow.primary_backend.repository.AvailableTriggerRepository;
import com.blinkflow.primary_backend.repository.FlowRepository;
import com.blinkflow.primary_backend.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class FlowService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FlowRepository flowRepo;

	@Autowired
	private AvailableTriggerRepository availableTriggerRepo;

	@Autowired
	private AvailableActionRepository availableActionRepo;

	public Optional<FlowResponseDTO> createFlow(@Valid FlowRequestDTO flowReq) {
		Optional<User> user = userRepo.findById(flowReq.getUserID());
		if (user.isEmpty())
			return Optional.empty();

		Flow flow = Flow.builder().name(flowReq.getName()).user(user.get()).build();

		Optional<AvailableTrigger> availableTriggerType = availableTriggerRepo
				.findById(flowReq.getAvailableTriggerID());
		if (availableTriggerType.isEmpty())
			return Optional.empty();
		FlowTrigger trigger = FlowTrigger.builder().metadata(flowReq.getTriggerMetadata())
				.triggerType(availableTriggerType.get()).flow(flow).build();

		List<FlowAction> actions = new ArrayList<FlowAction>();
		for (ActionDTO action : flowReq.getFlowActions()) {
			Optional<AvailableAction> availableActionType = availableActionRepo.findById(action.getAvailableActionID());
			if (availableActionType.isEmpty())
				return Optional.empty();
			FlowAction flowAction = FlowAction.builder().metadata(action.getMetadata()).flow(flow)
					.actionType(availableActionType.get()).sortingOrder(action.getSortingOrder()).build();
			actions.add(flowAction);
		}

		flow.setFlowTrigger(trigger);
		flow.setFlowActions(actions);
		Flow savedFlow = flowRepo.save(flow);
		FlowResponseDTO response = FlowMapper.toResponseDTO(savedFlow);
		return Optional.of(response);
	}

	public Optional<List<FlowResponseDTO>> getAllFlows() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		List<Flow> flows = flowRepo.findAllByUserId(userPrincipal.getUser().getId());
		if (flows == null)
			return Optional.empty();
		List<FlowResponseDTO> flowResponses = new ArrayList<FlowResponseDTO>();
		for (Flow flow : flows) {
			FlowResponseDTO response = FlowMapper.toResponseDTO(flow);
			flowResponses.add(response);
		}
		return Optional.of(flowResponses);
	}

	public Optional<FlowResponseDTO> getFlowByID(Long flowID) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Flow flow = flowRepo.findByUserIdAndId(userPrincipal.getUser().getId(), flowID);
		if (flow == null)
			return Optional.empty();
		FlowResponseDTO response = FlowMapper.toResponseDTO(flow);
		return Optional.of(response);
	}

	public Optional<String> deleteFlowByID(Long flowID) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Flow flow = flowRepo.findByUserIdAndId(userPrincipal.getUser().getId(), flowID);
		if (flow == null)
			return Optional.empty();
		flowRepo.deleteByUserIdAndId(userPrincipal.getUser().getId(), flowID);
		return Optional.of("Flow with ID:" + flow.getId() + " is deleted successfully.");
	}

	public Optional<FlowResponseDTO> updateFlowByID(Long flowID, @Valid FlowRequestDTO flowReq) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Flow flow = flowRepo.findByUserIdAndId(userPrincipal.getUser().getId(), flowID);
		
		if(flow == null)
			return Optional.empty();
		
		Flow newFlow = Flow.builder().name(flowReq.getName()).user(userPrincipal.getUser()).build();
		Optional<AvailableTrigger> availableTriggerType = availableTriggerRepo
				.findById(flowReq.getAvailableTriggerID());
		if (availableTriggerType.isEmpty())
			return Optional.empty();
		
		FlowTrigger trigger = FlowTrigger.builder().metadata(flowReq.getTriggerMetadata())
				.triggerType(availableTriggerType.get()).flow(newFlow).build();

		List<FlowAction> actions = new ArrayList<FlowAction>();
		for (ActionDTO action : flowReq.getFlowActions()) {
			Optional<AvailableAction> availableActionType = availableActionRepo.findById(action.getAvailableActionID());
			if (availableActionType.isEmpty())
				return Optional.empty();
			FlowAction flowAction = FlowAction.builder().metadata(action.getMetadata()).flow(flow)
					.actionType(availableActionType.get()).sortingOrder(action.getSortingOrder()).build();
			actions.add(flowAction);
		}
		
		newFlow.setFlowTrigger(trigger);
		newFlow.setFlowActions(actions);
		Flow savedFlow = flowRepo.save(newFlow);
		FlowResponseDTO response = FlowMapper.toResponseDTO(savedFlow);
		return Optional.of(response);
	}
}
