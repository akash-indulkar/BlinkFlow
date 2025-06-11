package com.blinkflow.primary_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.AvailableResponseDTO;
import com.blinkflow.primary_backend.model.AvailableTrigger;
import com.blinkflow.primary_backend.repository.AvailableTriggerRepository;

@Service
public class AvailableTriggerService {
	@Autowired
	private AvailableTriggerRepository triggerRepo;

	public Optional<List<AvailableResponseDTO>> getAvailableTrigger() {
		List<AvailableTrigger> availableTriggers = triggerRepo.findAll();
		if(availableTriggers == null) return Optional.empty();
		List<AvailableResponseDTO> response = new ArrayList<AvailableResponseDTO>();
		for(AvailableTrigger trigger : availableTriggers) {
			response.add(AvailableResponseDTO.builder().id(trigger.getId()).name(trigger.getName()).image(trigger.getImage()).build());
		}
		return Optional.of(response);
	}
}
