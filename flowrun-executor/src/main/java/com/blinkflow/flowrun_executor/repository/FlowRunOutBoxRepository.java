package com.blinkflow.flowrun_executor.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.flowrun_executor.model.FlowRunOutBox;

@Repository
public interface FlowRunOutBoxRepository extends JpaRepository<FlowRunOutBox, Long>{
	List<FlowRunOutBox> findTop10ByOrderByIdAsc();
}
