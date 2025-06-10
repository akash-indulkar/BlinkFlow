package com.blinkflow.primary_backend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.model.AvailableAction;
import com.blinkflow.primary_backend.repository.AvailableActionRepository;

@Service
public class AvailableActionService {
	@Autowired
	private AvailableActionRepository actionRepo;

	public Optional<List<AvailableAction>> getAvailableActions() {
		List<AvailableAction> availableActions = actionRepo.findAll();
		if(availableActions == null) return Optional.empty();
		return Optional.of(availableActions);
	}
}
