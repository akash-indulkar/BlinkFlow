package com.blinkflow.flowrun_listener.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blinkflow.flowrun_listener.dto.FlowRunResponseDTO;
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
	
	@Autowired
	public FlowRunService(FlowRepository flowRepo, FlowRunRepository flowRunRepo, FlowRunOutBoxRepository flowRunOutBoxRepo) {
		this.flowRepo = flowRepo;
		this.flowRunRepo = flowRunRepo;
		this.flowRunOutBoxRepo = flowRunOutBoxRepo;
	}

    @Transactional
	public Optional<FlowRunResponseDTO> intiateFlowRun(Long userID, Long flowID, Map<String, Object> requestBody, HttpServletRequest request) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		Flow flow = flowRepo.findByUserIdAndId(userID, flowID);
		if(flow == null) return Optional.empty();
		
		Object secretObj = flow.getFlowTrigger().getMetadata().get("secret");
		if(secretObj != null) {
			final String secret = String.valueOf(secretObj);
			Enumeration<String> headers = request.getHeaderNames();
			final String receivedSignature = SignatureGetter.getSignatureFromHeader(headers, request);
			if(receivedSignature == null) return Optional.empty();
			final String rawPayload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			if(rawPayload.isEmpty()) return Optional.empty();
			Boolean isSignatureValid = SignatureValidator.verifySignature(receivedSignature, secret, rawPayload);
			if(!isSignatureValid) return Optional.empty();
		}
		
		FlowRun flowRun = FlowRun.builder()
							.flow(flow)
							.metadata(requestBody)
							.status(FlowRunStatus.PENDING)
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
		return Optional.of(response);
	}

}
