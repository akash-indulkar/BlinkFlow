package com.blinkflow.primary_backend.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blinkflow.primary_backend.model.User;

@Repository
public interface UserRepositary extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	public User findByEmailAndPassword(String email, String password);
}
