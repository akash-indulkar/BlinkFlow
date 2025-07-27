package com.blinkflow.primary_backend.exception;

public class EntityNotFound extends RuntimeException{
	public EntityNotFound(String message) {
		super(message);
	}
}
