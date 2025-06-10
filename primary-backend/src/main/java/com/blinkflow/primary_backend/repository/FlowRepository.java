package com.blinkflow.primary_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blinkflow.primary_backend.model.Flow;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Integer>{
	List<Flow> findAllByUserId(Integer userId);
	Flow findByUserIdAndId(int userID, int flowID);
}
