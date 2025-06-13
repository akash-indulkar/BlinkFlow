package com.blinkflow.primary_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.FlowAction;

@Repository
public interface FlowActionRepository extends JpaRepository<FlowAction, Long> {

}
