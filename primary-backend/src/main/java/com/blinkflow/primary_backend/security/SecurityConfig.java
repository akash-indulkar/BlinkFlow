package com.blinkflow.primary_backend.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	private final JWTFilter jwtFilter;
    private final CustomOAuth2SuccessHandler successHandler;
    @Value("${frontend.url}")
    private String frontendURL;
    
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter, CustomOAuth2SuccessHandler successHandler) {
    	this.userDetailsService = userDetailsService;
    	this.jwtFilter = jwtFilter;
    	this.successHandler = successHandler;
    }
   
    @Value("${frontend.redirect.url}")
	private String frontendRedirectURL;
    
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
		
				http
				.cors().and()
				.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request -> request
													.requestMatchers("/user/signup", "/user/login", "/user/signup/verify", "/user/login/forget", "/user/login/reset", "/actions/availableactions", "/triggers/availabletriggers")
													.permitAll()
													.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.oauth2Login(oauth -> oauth.successHandler(successHandler)
											.failureHandler((request, response, exception) -> {
												response.sendRedirect(frontendRedirectURL + "?error=true");
											}));
		
		return http.build();
		
	}
    
    @Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
    @Bean
    public CorsConfigurationSource corsConfigurer(){
    	CorsConfiguration config = new CorsConfiguration();
    	config.setAllowedOrigins(List.of(frontendURL));
    	config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    	config.setAllowedHeaders(List.of("*"));
    	config.setAllowCredentials(true);
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", config);
    	return source;
    	
    }
} 