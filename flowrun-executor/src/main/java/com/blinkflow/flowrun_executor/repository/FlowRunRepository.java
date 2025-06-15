package com.blinkflow.flowrun_executor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.flowrun_executor.model.FlowRun;

@Repository
public interface FlowRunRepository extends JpaRepository<FlowRun, Long>{

}
