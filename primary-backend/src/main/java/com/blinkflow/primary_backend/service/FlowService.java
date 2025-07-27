package com.blinkflow.primary_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.blinkflow.primary_backend.exception.AuthenticationException;
import com.blinkflow.primary_backend.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blinkflow.primary_backend.dto.ActionDTO;
import com.blinkflow.primary_backend.dto.FlowRequestDTO;
import com.blinkflow.primary_backend.dto.FlowResponseDTO;
import com.blinkflow.primary_backend.exception.FlowException;
import com.blinkflow.primary_backend.mapper.FlowMapper;
import com.blinkflow.primary_backend.model.AvailableAction;
import com.blinkflow.primary_backend.model.AvailableTrigger;
import com.blinkflow.primary_backend.model.Flow;
import com.blinkflow.primary_backend.model.FlowAction;
import com.blinkflow.primary_backend.model.FlowTrigger;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.repository.AvailableActionRepository;
import com.blinkflow.primary_backend.repository.AvailableTriggerRepository;
import com.blinkflow.primary_backend.repository.FlowRepository;
import com.blinkflow.primary_backend.repository.UserRepository;
import com.blinkflow.primary_backend.util.AuthUtil;

import jakarta.validation.Valid;

@Service
public class FlowService {
	private final UserRepository userRepo;
	private final FlowRepository flowRepo;
	private final AvailableTriggerRepository availableTriggerRepo;
	private final AvailableActionRepository availableActionRepo;

	@Autowired
	public FlowService(UserRepository userRepo, FlowRepository flowRepo,
			AvailableTriggerRepository availableTriggerRepo, AvailableActionRepository availableActionRepo) {
		this.userRepo = userRepo;
		this.flowRepo = flowRepo;
		this.availableTriggerRepo = availableTriggerRepo;
		this.availableActionRepo = availableActionRepo;
	}

	@Transactional
	public FlowResponseDTO createFlow(@Valid FlowRequestDTO flowReq) {
		User user = userRepo.findById(flowReq.getUserID())
				.orElseThrow(() -> new AuthenticationException("User not found"));

		Flow flow = Flow.builder().name(flowReq.getName()).user(user).build();

		AvailableTrigger availableTriggerType = availableTriggerRepo.findById(flowReq.getAvailableTriggerID())
				.orElseThrow(() -> new EntityNotFound("Available Triggers not found"));

		FlowTrigger trigger = FlowTrigger.builder().metadata(flowReq.getTriggerMetadata())
				.triggerType(availableTriggerType).flow(flow).build();

		List<FlowAction> actions = new ArrayList<FlowAction>();
		for (ActionDTO action : flowReq.getFlowActions()) {
			AvailableAction availableActionType = availableActionRepo.findById(action.getAvailableActionID())
					.orElseThrow(() -> new EntityNotFound("Available Actions not found"));

			FlowAction flowAction = FlowAction.builder().metadata(action.getMetadata()).flow(flow)
					.actionType(availableActionType).sortingOrder(action.getSortingOrder()).build();
			actions.add(flowAction);
		}

		flow.setFlowTrigger(trigger);
		flow.setFlowActions(actions);
		try {
			Flow savedFlow = flowRepo.save(flow);
			FlowResponseDTO response = FlowMapper.toResponseDTO(savedFlow);
			return response;
		} catch (Exception e) {
			throw new FlowException("Failed to create flow");
		}
	}

	public List<FlowResponseDTO> getAllFlows() {
		return flowRepo.findAllByUserId(AuthUtil.getCurrentUserId()).stream()
				.map(flow -> FlowMapper.toResponseDTO(flow)).toList();
	}

	public FlowResponseDTO getFlowByID(Long flowID) {
		Flow flow = Optional.ofNullable(flowRepo.findByUserIdAndId(AuthUtil.getCurrentUserId(), flowID))
				.orElseThrow(() -> new FlowException("Flow not found"));
		return FlowMapper.toResponseDTO(flow);
	}

	@Transactional
	public Long deleteFlowByID(Long flowID) {
		Long userID = AuthUtil.getCurrentUserId();
		Flow flow = Optional.ofNullable(flowRepo.findByUserIdAndId(userID, flowID))
				.orElseThrow(() -> new EntityNotFound("Flow Not Found"));

		try {
			flowRepo.deleteByUserIdAndId(userID, flowID);
			return flow.getId();
		} catch (Exception e) {
			throw new FlowException("Failed to delete flow");
		}
	}

	@Transactional
	public FlowResponseDTO updateFlowByID(Long flowID, @Valid FlowRequestDTO flowReq) {
		Flow flow = Optional.ofNullable(flowRepo.findByUserIdAndId(AuthUtil.getCurrentUserId(), flowID))
				.orElseThrow(() -> new EntityNotFound("Flow Not Found"));

		flow.setName(flowReq.getName());

		AvailableTrigger availableTriggerType = availableTriggerRepo.findById(flowReq.getAvailableTriggerID())
				.orElseThrow(() -> new EntityNotFound("Available Trigger not found"));

		FlowTrigger trigger = flow.getFlowTrigger();
		trigger.setTriggerType(availableTriggerType);
		trigger.setMetadata(flowReq.getTriggerMetadata());

		List<FlowAction> actions = flow.getFlowActions();
		actions.clear();
		for (ActionDTO action : flowReq.getFlowActions()) {
			AvailableAction availableActionType = availableActionRepo.findById(action.getAvailableActionID())
					.orElseThrow(() -> new EntityNotFound("Available action not found"));
			FlowAction flowAction = FlowAction.builder().metadata(action.getMetadata()).flow(flow)
					.actionType(availableActionType).sortingOrder(action.getSortingOrder()).build();
			actions.add(flowAction);
		}

		try {
			Flow savedFlow = flowRepo.save(flow);
			FlowResponseDTO response = FlowMapper.toResponseDTO(savedFlow);
			return response;
		} catch (Exception e) {
			throw new FlowException("Failed update flow");
		}
	}
}
