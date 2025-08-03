package com.blinkflow.primary_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.blinkflow.primary_backend.exception.AuthenticationException;

@Service
public class EmailService {
	private final JavaMailSender mailSender;
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Value("${spring.mail.username}")
	private String fromEmailID;
	
	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendEmail(String email, String subject, String body) throws AuthenticationException {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(subject);
		msg.setText(body);
		msg.setFrom(fromEmailID);
		try {
			mailSender.send(msg);
		} catch(Exception e) {
			logger.error("Email Sender error : " + e.getMessage());
			throw new AuthenticationException("Failed to send Email");
		}
	}
}
