package com.blinkflow.primary_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.model.UserPrincipal;
import com.blinkflow.primary_backend.repositary.UserRepositary;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepositary urepo;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = urepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(user);
	}
}
