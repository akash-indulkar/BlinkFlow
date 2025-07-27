package com.blinkflow.primary_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.model.AvailableAction;
import com.blinkflow.primary_backend.repository.AvailableActionRepository;

@Service
public class AvailableActionService {
	private final AvailableActionRepository actionRepo;
	
	@Autowired
	public AvailableActionService(AvailableActionRepository actionRepo) {
		this.actionRepo = actionRepo;
	}

	public Optional<List<AvailableResponseDTO>> getAvailableActions() {
		List<AvailableAction> availableActions = actionRepo.findAll();
		if(availableActions == null) return Optional.empty();
		List<AvailableResponseDTO> response = new ArrayList<AvailableResponseDTO>();
		for(AvailableAction action : availableActions) {
			response.add(AvailableResponseDTO.builder().id(action.getId()).name(action.getName()).image(action.getImage()).build());
		}
		return Optional.of(response);
	}
}
