package com.blinkflow.primary_backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.model.User;
import com.blinkflow.primary_backend.model.UserPrincipal;
import com.blinkflow.primary_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository urepo;
	
	@Autowired
	public CustomUserDetailsService(UserRepository urepo) {
		this.urepo = urepo;
	}
	
	public UserDetails loadUserById(Long userID) throws UsernameNotFoundException {
		Optional<User> user = urepo.findById(userID);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(user.get());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = urepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(user);
	}
}
