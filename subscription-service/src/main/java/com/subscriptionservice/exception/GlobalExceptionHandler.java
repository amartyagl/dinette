package com.subscriptionservice.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RefreshScope
public class GlobalExceptionHandler {

	@Autowired
	Environment environment;

	@ExceptionHandler(SubscriptionAlreadyExistException.class)
	public ResponseEntity<ErrorInfo> handleExceptionForSubscriptionAlreadyExist(
			SubscriptionAlreadyExistException subscriptionAlreadyExistException) {
		ErrorInfo info = new ErrorInfo("101", environment.getProperty(subscriptionAlreadyExistException.getMessage()),
				LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SubscriptionNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleExceptionForSubscriptionNotFound(
			SubscriptionNotFoundException subscriptionNotFoundException) {
		ErrorInfo info = new ErrorInfo("102", environment.getProperty(subscriptionNotFoundException.getMessage()),
				LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}
}
