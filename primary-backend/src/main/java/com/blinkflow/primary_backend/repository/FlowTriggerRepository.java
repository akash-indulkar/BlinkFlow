package com.blinkflow.primary_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.FlowTrigger;

@Repository
public interface FlowTriggerRepository extends JpaRepository<FlowTrigger, Long>{

}
