package com.blinkflow.primary_backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.mapper.UserMapper;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.model.UserPrincipal;
import com.blinkflow.primary_backend.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired
	private UserRepository urepo;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authManager;

	public Optional<UserResponseDTO> authenticateUser(@Valid UserRequestDTO reqUser) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword()));
		if (!auth.isAuthenticated()) {
			return Optional.empty();
		}
		User user = urepo.findByEmail(reqUser.getEmail());
		String token = jwtService.generateToken(user.getId());
		UserResponseDTO response = UserMapper.toResponseDTO(user);
		response.setToken(token);
		return Optional.of(response);
	}

	public Optional<UserResponseDTO> createUser(@Valid UserRequestDTO reqUser) {
		User dbUser = urepo.findByEmail(reqUser.getEmail());
		if (dbUser != null)
			return Optional.empty();
		User newUser = UserMapper.toEntity(reqUser);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		User savedUser = urepo.save(newUser);
		String token = jwtService.generateToken(savedUser.getId());
		UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
		response.setToken(token);
		return Optional.of(response);
	}

	public Optional<UserResponseDTO> getMyInformation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User user = urepo.findById(userPrincipal.getUser().getId()).get();
		if (user == null)
			return Optional.empty();
		UserResponseDTO response = UserMapper.toResponseDTO(user);
		return Optional.of(response);
	}

	public Optional<UserResponseDTO> authenticateUserUsingOAuth(OAuth2User oAuth2User) {
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");
		User dbUser = urepo.findByEmail(email);
		if (dbUser != null && dbUser.getProvider().equals("Google")) {
			String token = jwtService.generateToken(dbUser.getId());
			UserResponseDTO response = UserMapper.toResponseDTO(dbUser);
			response.setToken(token);
			return Optional.of(response);
		}
		User newUser = User.builder().email(email).name(name).provider("Google").build();
		User savedUser = urepo.save(newUser);
		String token = jwtService.generateToken(savedUser.getId());
		UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
		response.setToken(token);
		return Optional.of(response);
	}
}
