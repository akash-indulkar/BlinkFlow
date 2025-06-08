package com.blinkflow.primary_backend.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.AvailableTrigger;

@Repository
public interface AvailableTriggerRepositary extends JpaRepository<AvailableTrigger, Integer>{

}
