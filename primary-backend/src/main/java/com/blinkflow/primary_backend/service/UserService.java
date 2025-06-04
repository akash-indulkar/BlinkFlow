package com.blinkflow.primary_backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.mapper.UserMapper;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.repositary.UserRepositary;

import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired
	UserRepositary urepo;
	
	public Optional<UserResponseDTO> createUser(@Valid UserRequestDTO reqUser) {
		User dbUser = urepo.findByEmail(reqUser.getEmail());
		if(dbUser != null) return null;
		User newUser = UserMapper.toEntity(reqUser);
		User savedUser = urepo.save(newUser);
		UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
		return Optional.of(response);
	}

	public Optional<UserResponseDTO> verifyUser(@Valid UserRequestDTO reqUser) {
		User dbUser = urepo.findByEmailAndPassword(reqUser.getEmail(), reqUser.getPassword());
		if(dbUser == null) return null;
		UserResponseDTO response = UserMapper.toResponseDTO(dbUser);
		return Optional.of(response);
	}

	public Optional<UserResponseDTO> getUserInformation(@Valid int id) {
		User user = urepo.findById(id).get();
		if(user == null) return null;
		UserResponseDTO response = UserMapper.toResponseDTO(user);
		return Optional.of(response);
	}
}
