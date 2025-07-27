package com.blinkflow.primary_backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.repository.AvailableTriggerRepository;

@Service
public class AvailableTriggerService {
	private final AvailableTriggerRepository triggerRepo;

	@Autowired
	public AvailableTriggerService(AvailableTriggerRepository triggerRepo) {
		this.triggerRepo = triggerRepo;
	}
	
	public List<AvailableResponseDTO> getAvailableTrigger() {
		return triggerRepo.findAll().stream()
				.map(trigger -> AvailableResponseDTO.builder()
						.id(trigger.getId())
						.name(trigger.getName())
						.image(trigger.getImage())
						.build())
						.toList();
	}
}
