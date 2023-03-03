package com.menuservice.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RefreshScope
public class GlobalExceptionHandler {

	@Autowired
	Environment environment;

	/*
	 * This Method is to handle exception when Resource not found
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleExceptionForResorceNotFound(
			ResourceNotFoundException resourceNotFoundException) {
		ErrorInfo info = new ErrorInfo("101", environment.getProperty(resourceNotFoundException.getMessage()),
				LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
	}

	/*
	 * This Method is to handle exception when Food Name Already Exists
	 */
	@ExceptionHandler(FoodNameAlreadyExistException.class)
	public ResponseEntity<ErrorInfo> foodNameAlreadyExistException(
			FoodNameAlreadyExistException foodNameAlreadyExistException) {
		ErrorInfo info = new ErrorInfo("102", environment.getProperty(foodNameAlreadyExistException.getMessage()),
				LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}

	/*
	 * This Method is to handle Validation Exception.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorInfo> constraintViolationException(
			ConstraintViolationException constraintViolationException) {
		Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
		String errorMessage = null;
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errorMessage = constraintViolation.getMessageTemplate();
		}
		ErrorInfo info = new ErrorInfo("102", errorMessage, LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}

	/*
	 * This Method is to handle exception where enum fields Message is not as per
	 * the Default Message
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorInfo> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException httpMessageNotReadableException) {
		ErrorInfo info = new ErrorInfo("103", httpMessageNotReadableException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
	}

	/*
	 * This Method is to handle Validation Exception.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		List<String> errorMap = new ArrayList<>();
		methodArgumentNotValidException.getBindingResult().getFieldErrors()
				.forEach(error -> errorMap.add(error.getDefaultMessage()));
		ErrorInfo info = null;
		for (String message : errorMap) {
			info = new ErrorInfo("104", message, LocalDateTime.now());
		}
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(SchedulerFailedException.class)
	public ResponseEntity<ErrorInfo> handleSchedulerException(
			SchedulerFailedException schedulerException) {
		ErrorInfo info = new ErrorInfo("105", schedulerException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(TableNotScheduledException.class)
	public ResponseEntity<ErrorInfo> handleTableNotScheduledException(
			TableNotScheduledException tableNotScheduledException) {
		ErrorInfo info = new ErrorInfo("106", tableNotScheduledException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
	}

	/*
	 * This Method is to handle general exception
	 */
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorInfo> handleGlobalException(Exception exception) {
//		ErrorInfo info = new ErrorInfo("106", exception.getMessage(), LocalDateTime.now());
//		return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}
