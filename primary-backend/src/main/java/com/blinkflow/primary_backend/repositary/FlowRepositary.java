package com.blinkflow.primary_backend.repositary;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.Flow;

@Repository
public interface FlowRepositary extends JpaRepository<Flow, Integer>{
	List<Flow> findAllByUserId(Integer userId);
	Flow findByUserIdAndId(int id, int flowID);
}
