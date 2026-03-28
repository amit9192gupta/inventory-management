package com.project.inventory.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {

		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("message", ex.getMessage());
		error.put("status", HttpStatus.NOT_FOUND.value());

		return error;
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleStockException(ResourceNotFoundException ex) {

		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("message", ex.getMessage());
		error.put("status", HttpStatus.BAD_REQUEST.value());

		return error;
	}
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, Object> handleOptimisticLock(ObjectOptimisticLockingFailureException ex) {

	    Map<String, Object> error = new HashMap<>();
	    error.put("timestamp", LocalDateTime.now());
	    error.put("message", "Concurrent update detected. Please try again.");
	    error.put("status", HttpStatus.CONFLICT.value());

	    return error;
	}
}
