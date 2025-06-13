package com.blinkflow.primary_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.AvailableAction;

@Repository
public interface AvailableActionRepository extends JpaRepository<AvailableAction, Long> {
	
}
