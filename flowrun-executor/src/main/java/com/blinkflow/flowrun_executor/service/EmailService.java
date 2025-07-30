package com.blinkflow.flowrun_executor.service;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blinkflow.flowrun_executor.exception.ActionExecutionException;
import com.blinkflow.flowrun_executor.util.MetadataFormatter;

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
	
	public void sendEmail(Map<String, Object> flowRunMetadata, Map<String, Object> emailMetadata) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailMetadata.get("emailID").toString());
		msg.setSubject(emailMetadata.get("subject").toString());
		String serializableMetadataMessage = MetadataFormatter.toPrettyJson(flowRunMetadata);
		msg.setText(serializableMetadataMessage);
		msg.setFrom(fromEmailID);
		try {
			mailSender.send(msg);
		} catch(Exception e) {
			logger.error("Email Sender error : " + e.getMessage());
			throw new ActionExecutionException("Failed to execute Email action");
		}
	}
}
