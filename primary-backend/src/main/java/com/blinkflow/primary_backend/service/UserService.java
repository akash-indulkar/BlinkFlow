package com.blinkflow.primary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.blinkflow.primary_backend.dto.ResetOTPVerificationRequest;
import com.blinkflow.primary_backend.dto.SignupOTPVerificationRequest;
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
	private final EmailOTPService OTPService;

	@Autowired
	public UserService(UserRepository urepo, JWTService jwtService, BCryptPasswordEncoder encoder,
			AuthenticationManager authManager, EmailOTPService OTPService) {
		this.urepo = urepo;
		this.jwtService = jwtService;
		this.encoder = encoder;
		this.authManager = authManager;
		this.OTPService = OTPService;
	}

	public UserResponseDTO authenticateUser(UserRequestDTO reqUser) {
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
			throw new AuthenticationException("Invalid email or password: " + e.getMessage());
		}
	}

	public String createUser(UserRequestDTO reqUser) {
		try {
			User dbUser = urepo.findByEmail(reqUser.getEmail());
			if (dbUser != null)
				throw new RuntimeException("User already exists, Please login.");
			OTPService.sendSignupOTP(reqUser);
			return "Signup verification OTP sent successfully";
		} catch (Exception e) {
			throw new AuthenticationException("Failed to send signup verification OTP: " + e.getMessage());
		}
	}

	public String forgetPassword(@Valid String emailID) {
		try {
			User dbUser = urepo.findByEmail(emailID);
			if (dbUser == null)
				throw new RuntimeException("User does not exists, Please signup.");
			OTPService.sendPasswordResetOTP(emailID);
			return "Password reset OTP sent successfully";
		} catch (Exception e) {
			throw new AuthenticationException("Failed to send password reset verification OTP: " + e.getMessage());
		}
	}

	public UserResponseDTO verifySignupUser(SignupOTPVerificationRequest OTPreq) {
		try {
			if (OTPService.verifyOtp(OTPreq.getEmailID(), OTPreq.getOTP())) {
				UserRequestDTO reqUser = OTPService.getSignupUserDetailsFromRedis(OTPreq.getEmailID());
				User newUser = UserMapper.toEntity(reqUser);
				newUser.setPassword(encoder.encode(newUser.getPassword()));
				User savedUser = urepo.save(newUser);
				String token = jwtService.generateToken(savedUser.getId());
				UserResponseDTO response = UserMapper.toResponseDTO(savedUser);
				response.setToken(token);
				OTPService.clearRedis(savedUser.getEmail());
				return response;
			} else {
				throw new AuthenticationException("OTP verification failed");
			}
		} catch (Exception e) {
			throw new AuthenticationException("User creation failed: " + e.getMessage());
		}
	}

	public UserResponseDTO resetPassword(ResetOTPVerificationRequest OTPreq) {
		try {
			if (OTPService.verifyOtp(OTPreq.getEmailID(), OTPreq.getOTP())) {
				User user = urepo.findByEmail(OTPreq.getEmailID());
				user.setPassword(encoder.encode(OTPreq.getPassword()));
				User updatedUser = urepo.save(user);
				String token = jwtService.generateToken(updatedUser.getId());
				UserResponseDTO response = UserMapper.toResponseDTO(updatedUser);
				response.setToken(token);
				OTPService.clearRedis(user.getEmail());
				return response;
			} else {
				throw new AuthenticationException("OTP verificatin failed");
			}
		} catch (Exception e) {
			throw new AuthenticationException("Failed to reset password: " + e.getMessage());
		}
	}

	public UserResponseDTO getMyInformation() {
		try {
			User user = urepo.findById(AuthUtil.getCurrentUserId())
					.orElseThrow(() -> new RuntimeException("User not found"));
			UserResponseDTO response = UserMapper.toResponseDTO(user);
			return response;
		} catch (Exception e) {
			throw new AuthenticationException("User not found: " + e.getMessage());
		}
	}

	public UserResponseDTO authenticateUserUsingOAuth(OAuth2User OAuth2User) {
		try {
			String email = OAuth2User.getAttribute("email");
			String name = OAuth2User.getAttribute("name");
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
			throw new AuthenticationException("Authentication failed with OAuth: " + e.getMessage());
		}
	}
}
