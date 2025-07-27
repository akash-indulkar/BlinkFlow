package com.blinkflow.flowrun_listener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException e) {
        logger.error(e.getMessage());
    	return ResponseEntity.status(401)
            .body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFound e) {
    	logger.error(e.getMessage());
    	return ResponseEntity.status(404)
            .body(e.getMessage());
    }
    
    @ExceptionHandler(FlowRunException.class)
    public ResponseEntity<String> handleFlowRunException(FlowRunException e) {
    	logger.error(e.getMessage());
    	return ResponseEntity.status(404)
            .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
    	logger.error(e.getMessage());
    	return ResponseEntity.status(500)
            .body("Internal server error: " + e.getMessage());
    }
}
