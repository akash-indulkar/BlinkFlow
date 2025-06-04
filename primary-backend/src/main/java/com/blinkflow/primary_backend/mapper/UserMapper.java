package com.blinkflow.primary_backend.mapper;

import com.blinkflow.primary_backend.dto.UserRequestDTO;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.model.User;

public class UserMapper {
	public static User toEntity(UserRequestDTO reqUser) {
		return User.builder()
				.email(reqUser.getEmail())
				.password(reqUser.getPassword())
				.name(reqUser.getName())
				.provider("Local provider")
				.build();
	}
	
	public static UserResponseDTO toResponseDTO(User user) {
		return UserResponseDTO.builder()
				.id(user.getId())
				.email(user.getEmail())
				.name(user.getName())
				.build();
	}
}
