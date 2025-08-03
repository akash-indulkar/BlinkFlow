package com.blinkflow.primary_backend.service;

import java.time.Duration;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.exception.AuthenticationException;
import com.blinkflow.primary_backend.model.User;
import jakarta.validation.Valid;

@Service
public class EmailOTPService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final EmailService mailService;

	@Autowired
	public EmailOTPService(EmailService mailService, RedisTemplate<String, Object> redisTemplate) {
		this.mailService = mailService;
		this.redisTemplate = redisTemplate;
	}

	public void sendSignupOTP(UserRequestDTO reqUser) {
		Integer OTPcount = (Integer) redisTemplate.opsForValue().get("OTP_COUNT:" + reqUser.getEmail());
		if (OTPcount == null) {
			redisTemplate.opsForValue().set("OTP_COUNT:" + reqUser.getEmail(), 1, Duration.ofMinutes(10));
		} else if (OTPcount >= 3) {
			throw new AuthenticationException("OTP limit exceeded");
		} else {
			redisTemplate.opsForValue().increment("OTP_COUNT:" + reqUser.getEmail());
		}

		redisTemplate.opsForValue().set("TEMP_USER_NAME:" + reqUser.getEmail(), reqUser.getName(),
				Duration.ofMinutes(10));
		redisTemplate.opsForValue().set("TEMP_USER_PASSWORD:" + reqUser.getEmail(), reqUser.getPassword(),
				Duration.ofMinutes(10));

		String OTP = String.format("%6d", new Random().nextInt(999999));
		redisTemplate.opsForValue().set("OTP:" + reqUser.getEmail(), OTP, Duration.ofMinutes(5));

		mailService.sendEmail(reqUser.getEmail(), "One Time Password", "Your OTP for signup is: " + OTP);
	}

	public void sendPasswordResetOTP(@Valid String emailID, User dbUser) {
		Integer OTPcount = (Integer) redisTemplate.opsForValue().get("OTP_COUNT:" + emailID);
		if (OTPcount == null) {
			redisTemplate.opsForValue().set("OTP_COUNT:" + emailID, 1, Duration.ofMinutes(10));
		} else if (OTPcount >= 3) {
			throw new AuthenticationException("OTP limit exceeded");
		} else {
			redisTemplate.opsForValue().increment("OTP_COUNT:" + emailID);
		}

		String OTP = String.format("%6d", new Random().nextInt(999999));
		redisTemplate.opsForValue().set("OTP:" + emailID, OTP, Duration.ofMinutes(5));
		redisTemplate.opsForValue().set("DBUSER:" + emailID, dbUser);

		mailService.sendEmail(emailID, "One Time Password", "Your OTP for password reset is: " + OTP);
	}

	public boolean verifyOtp(String email, String inputOtp) {
		String savedOtp = (String) redisTemplate.opsForValue().get("OTP:" + email);
		return savedOtp != null && savedOtp.equals(inputOtp);
	}

	public UserRequestDTO getSignupUserDetailsFromRedis(String email) {
		String name = (String) redisTemplate.opsForValue().get("TEMP_USER_NAME:" + email);
		String password = (String) redisTemplate.opsForValue().get("TEMP_USER_PASSWORD:" + email);
		return UserRequestDTO.builder().email(email).name(name).password(password).build();
	}

	public User getPasswordResetUserDetails(String emailID) {
		return (User) redisTemplate.opsForValue().get("DBUSER:" + emailID);
	}

	public void clearRedis(String email) {
		redisTemplate.delete("OTP:" + email);
		redisTemplate.delete("TEMP_USER:" + email);
	}
}
