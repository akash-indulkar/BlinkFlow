package com.blinkflow.flowrun_listener.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blinkflow.flowrun_listener.util.MetadataFormatter;
import com.blinkflow.flowrun_listener.dto.FlowRunResponseDTO;
import com.blinkflow.flowrun_listener.exception.AuthenticationException;
import com.blinkflow.flowrun_listener.exception.EntityNotFound;
import com.blinkflow.flowrun_listener.exception.FlowRunException;
import com.blinkflow.flowrun_listener.model.Flow;
import com.blinkflow.flowrun_listener.model.FlowRun;
import com.blinkflow.flowrun_listener.model.FlowRunOutBox;
import com.blinkflow.flowrun_listener.model.enums.FlowRunStatus;
import com.blinkflow.flowrun_listener.repository.FlowRepository;
import com.blinkflow.flowrun_listener.repository.FlowRunOutBoxRepository;
import com.blinkflow.flowrun_listener.repository.FlowRunRepository;
import com.blinkflow.flowrun_listener.util.SignatureGetter;
import com.blinkflow.flowrun_listener.util.SignatureValidator;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class FlowRunService {
	private final FlowRepository flowRepo;
	private final FlowRunRepository flowRunRepo;
	private final FlowRunOutBoxRepository flowRunOutBoxRepo;
	private final MetadataFormatter formatter;
	
	@Autowired
	public FlowRunService(FlowRepository flowRepo, FlowRunRepository flowRunRepo, FlowRunOutBoxRepository flowRunOutBoxRepo, MetadataFormatter formatter) {
		this.flowRepo = flowRepo;
		this.flowRunRepo = flowRunRepo;
		this.flowRunOutBoxRepo = flowRunOutBoxRepo;
		this.formatter = formatter;
	}

    @Transactional
	public FlowRunResponseDTO intiateFlowRun(Long userID, Long flowID, Map<String, Object> requestBody, HttpServletRequest request) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		Flow flow = Optional.ofNullable(flowRepo.findByUserIdAndId(userID, flowID))
				.orElseThrow(() -> new FlowRunException("Flow not found"));
		
		Object secretObj = flow.getFlowTrigger().getMetadata().get("secret");
		if(secretObj != null) {
			final String secret = String.valueOf(secretObj);
			Enumeration<String> headers = request.getHeaderNames();
			final String receivedSignature = Optional.ofNullable(SignatureGetter.getSignatureFromHeader(headers, request))
												.orElseThrow(() -> new AuthenticationException("SHA256 Signature not found"));
			
			byte[] rawPayloadBytes = request.getInputStream().readAllBytes();
			if (rawPayloadBytes.length == 0) {
	            throw new EntityNotFound("Empty request body received");
	        }
			Boolean isSignatureValid = SignatureValidator.verifySignature(receivedSignature, secret, rawPayloadBytes);
			if(!isSignatureValid) throw new AuthenticationException("Invalid SHA256 Signature");
		}
		
		try {
			String prettyFlowRunMetadataMessage = formatter.metadataToPrettyMessage(requestBody);
			FlowRun flowRun = FlowRun.builder()
					.flow(flow)
					.metadata(requestBody)
					.status(FlowRunStatus.PENDING)
					.prettyFlowRunMetadataMessage(prettyFlowRunMetadataMessage)
					.build();
			FlowRun savedFlowRun = flowRunRepo.save(flowRun);
			
			FlowRunOutBox flowRunOutBox = FlowRunOutBox.builder()
					.flowRunID(savedFlowRun.getId())
					.build();
			FlowRunOutBox savedFlowRunOutBox = flowRunOutBoxRepo.save(flowRunOutBox);
			
			FlowRunResponseDTO response = FlowRunResponseDTO.builder()
					.flowRunID(savedFlowRun.getId())
					.status(savedFlowRun.getStatus())
					.message("FlowRun has been initiated")
					.build();
			return response;
		} catch (Exception e) {
			throw new FlowRunException("Failed to run the flow");
		}
	}

}
