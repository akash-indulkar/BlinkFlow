package com.blinkflow.primary_backend.security;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.blinkflow.primary_backend.dto.UserResponseDTO;
import com.blinkflow.primary_backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private UserService userService;
	
	@Value("${frontend.redirect.url}")
	private String frontendRedirectURL;
	
	public CustomOAuth2SuccessHandler(@Lazy UserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

		UserResponseDTO responseDTO = userService.authenticateUserUsingOAuth(oAuth2User);
		String redirectUrl = frontendRedirectURL + "?token=" + responseDTO.getToken();
		response.sendRedirect(redirectUrl);
	}
}
