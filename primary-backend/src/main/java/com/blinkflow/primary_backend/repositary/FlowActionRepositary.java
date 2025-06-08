package com.blinkflow.primary_backend.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.FlowAction;

@Repository
public interface FlowActionRepositary extends JpaRepository<FlowAction, Integer> {

}
