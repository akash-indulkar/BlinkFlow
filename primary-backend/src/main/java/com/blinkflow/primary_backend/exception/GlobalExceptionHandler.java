package com.blinkflow.primary_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.blinkflow.primary_backend.dto.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<String>> handleAuthException(AuthenticationException e) {
        return ResponseEntity.status(401)
            .body(new APIResponse<>(e.getMessage(), null));
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<APIResponse<String>> handleEntityNotFound(EntityNotFound e) {
        return ResponseEntity.status(404)
            .body(new APIResponse<>(e.getMessage(), null));
    }
    
    @ExceptionHandler(FlowException.class)
    public ResponseEntity<APIResponse<String>> handleFlowException(FlowException e) {
        return ResponseEntity.status(404)
            .body(new APIResponse<>(e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleGeneral(Exception e) {
        return ResponseEntity.status(500)
            .body(new APIResponse<>("Internal server error", e.getMessage()));
    }
}
