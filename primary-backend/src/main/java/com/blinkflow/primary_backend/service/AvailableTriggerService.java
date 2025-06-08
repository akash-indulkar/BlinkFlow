package com.blinkflow.primary_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blinkflow.primary_backend.model.AvailableTrigger;
import com.blinkflow.primary_backend.repositary.AvailableTriggerRepositary;

@Service
public class AvailableTriggerService {
	@Autowired
	private AvailableTriggerRepositary triggerRepo;

	public Optional<List<AvailableTrigger>> getAvailableTrigger() {
		List<AvailableTrigger> availableTriggers = triggerRepo.findAll();
		if(availableTriggers == null) return Optional.empty();
		return Optional.of(availableTriggers);
	}
}
