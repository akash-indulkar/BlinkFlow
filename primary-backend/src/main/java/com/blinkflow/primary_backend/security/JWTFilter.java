package com.blinkflow.primary_backend.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.blinkflow.primary_backend.service.CustomUserDetailsService;
import com.blinkflow.primary_backend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CustomUserDetailsService myUser;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		Long userID = null;
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.split(" ")[1];
			userID = jwtService.extractUserID(token);
		}
		
		if(userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = myUser.loadUserById(userID);
			if(jwtService.validateToken(token, userID)) {
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
}

