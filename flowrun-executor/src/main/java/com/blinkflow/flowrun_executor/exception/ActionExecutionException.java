package com.blinkflow.flowrun_executor.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class ActionExecutionException extends RuntimeException {
	public ActionExecutionException(String message) {
		super(message);
	}
}
