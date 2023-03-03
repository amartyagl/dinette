package com.orderservice.exception;

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

	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleExceptionForOrderNotFound(
			OrderNotFoundException orderNotFoundException) {
		ErrorInfo info = new ErrorInfo("101",environment.getProperty(orderNotFoundException.getMessage()),LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorInfo> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException httpMessageNotReadableException) {
		ErrorInfo info = new ErrorInfo("102",environment.getProperty(httpMessageNotReadableException.getMessage()),LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}
}
