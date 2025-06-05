package com.blinkflow.primary_backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private UserRepositary urepo;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public Optional<UserResponseDTO> authenticateUser(@Valid UserRequestDTO reqUser) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
		if(!auth.isAuthenticated()) {
			return Optional.empty();
		}
		String token = jwtService.generateToken(reqUser.getEmail());
		User user = urepo.findByEmail(reqUser.getEmail());
		UserResponseDTO response = UserMapper.toResponseDTO(user);
		response.setToken(token);
		return Optional.of(response);
	}
	
	public Optional<UserResponseDTO> createUser(@Valid UserRequestDTO reqUser) {
		User dbUser = urepo.findByEmail(reqUser.getEmail());
		if(dbUser != null) return Optional.empty();
		User newUser = UserMapper.toEntity(reqUser);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		User savedUser = urepo.save(newUser);
		String token = jwtService.generateToken(savedUser.getEmail());
		UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
		response.setToken(token);
		return Optional.of(response);
	}


	public Optional<UserResponseDTO> getUserInformation(@Valid int id) {
		User user = urepo.findById(id).get();
		if(user == null) return Optional.empty();
		UserResponseDTO response = UserMapper.toResponseDTO(user);
		return Optional.of(response);
	}
}
