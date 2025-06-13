package com.blinkflow.flowrun_listener.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.flowrun_listener.model.Flow;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Long>{
	List<Flow> findAllByUserId(Long userId);
	Flow findByUserIdAndId(Long userID, Long flowID);
}
