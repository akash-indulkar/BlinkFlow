package com.blinkflow.primary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.exception.AuthenticationException;
import com.blinkflow.primary_backend.mapper.UserMapper;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.repository.UserRepository;
import com.blinkflow.primary_backend.util.AuthUtil;
import jakarta.validation.Valid;

@Service
public class UserService {
	private final UserRepository urepo;
	private final JWTService jwtService;
	private final BCryptPasswordEncoder encoder;
	private final AuthenticationManager authManager;

	@Autowired
	public UserService(UserRepository urepo, JWTService jwtService, BCryptPasswordEncoder encoder,
			AuthenticationManager authManager) {
		this.urepo = urepo;
		this.jwtService = jwtService;
		this.encoder = encoder;
		this.authManager = authManager;
	}

	public UserResponseDTO authenticateUser(@Valid UserRequestDTO reqUser) {
		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
			if (!auth.isAuthenticated()) {
				throw new RuntimeException("Authentication failed!");
			}
			User user = urepo.findByEmail(reqUser.getEmail());
			String token = jwtService.generateToken(user.getId());

			UserResponseDTO response = UserMapper.toResponseDTO(user);
			response.setToken(token);
			return response;
		} catch (Exception e) {
			throw new AuthenticationException("Invalid email or password.");
		}
	}

	public UserResponseDTO createUser(@Valid UserRequestDTO reqUser) {
		try {
			User dbUser = urepo.findByEmail(reqUser.getEmail());
			if (dbUser != null)
				throw new RuntimeException("User already exists, Please login.");
			User newUser = UserMapper.toEntity(reqUser);
			newUser.setPassword(encoder.encode(newUser.getPassword()));
			User savedUser = urepo.save(newUser);
			String token = jwtService.generateToken(savedUser.getId());
			UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
			response.setToken(token);
			return response;
		} catch (Exception e) {
			throw new AuthenticationException("User creation failed.");
		}
	}

	public UserResponseDTO getMyInformation() {
		try {
			User user = urepo.findById(AuthUtil.getCurrentUserId())
					.orElseThrow(()-> new RuntimeException("User not found"));
			UserResponseDTO response = UserMapper.toResponseDTO(user);
			return response;
		} catch (Exception e) {
			throw new AuthenticationException("User not found");
		}
	}

	public UserResponseDTO authenticateUserUsingOAuth(OAuth2User oAuth2User) {
		try {
			String email = oAuth2User.getAttribute("email");
			String name = oAuth2User.getAttribute("name");
			User dbUser = urepo.findByEmail(email);
			if (dbUser != null && dbUser.getProvider().equals("Google")) {
				String token = jwtService.generateToken(dbUser.getId());
				UserResponseDTO response = UserMapper.toResponseDTO(dbUser);
				response.setToken(token);
				return response;
			}
			User newUser = User.builder().email(email).name(name).provider("Google").build();
			User savedUser = urepo.save(newUser);
			String token = jwtService.generateToken(savedUser.getId());
			UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
			response.setToken(token);
			return response;
		} catch (Exception e) {
			throw new AuthenticationException("Authentication failed with OAuth");
		}
	}
}
