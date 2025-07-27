package com.blinkflow.primary_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.repository.AvailableActionRepository;

@Service
public class AvailableActionService {
	private final AvailableActionRepository actionRepo;

	@Autowired
	public AvailableActionService(AvailableActionRepository actionRepo) {
		this.actionRepo = actionRepo;
	}

	public List<AvailableResponseDTO> getAvailableActions() {
		return actionRepo.findAll().stream()
				.map(action -> AvailableResponseDTO.builder()
						.id(action.getId())
						.name(action.getName())
						.image(action.getImage())
						.build())
						.toList();
	}
}
