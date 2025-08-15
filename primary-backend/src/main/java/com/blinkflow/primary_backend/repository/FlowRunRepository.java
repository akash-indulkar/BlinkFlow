package com.blinkflow.primary_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.FlowRun;

@Repository
public interface FlowRunRepository extends JpaRepository<FlowRun, Long>{
	List<FlowRun> findByFlowId(Long flowId);
}
