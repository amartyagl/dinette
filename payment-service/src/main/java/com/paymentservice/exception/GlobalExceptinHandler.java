package com.paymentservice.exception;

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

import com.razorpay.RazorpayException;

@RestControllerAdvice
@RefreshScope
public class GlobalExceptinHandler {

	@Autowired
	Environment environment;

	/*
	 * This Method is to handle exception when payment history not found
	 */
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleExceptionForPaymentNotFound(
			PaymentNotFoundException paymentNotFoundException) {
		ErrorInfo info = new ErrorInfo("111", environment.getProperty(paymentNotFoundException.getMessage()),
				LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
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
		ErrorInfo info = new ErrorInfo("112", errorMessage, LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}

	/*
	 * This Method is to handle exception when Payment Status Message is not as per
	 * the Default Message
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorInfo> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException httpMessageNotReadableException) {
		ErrorInfo info = new ErrorInfo("113", httpMessageNotReadableException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
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
			info = new ErrorInfo("114", message, LocalDateTime.now());
		}
		return new ResponseEntity<>(info, HttpStatus.CONFLICT);
	}
	
	/*
	 * This method is to handle Razor Pay Exception.
	 */
	@ExceptionHandler(RazorpayException.class)
	public ResponseEntity<ErrorInfo> handleRazorpayException(RazorpayException razorpayException) {
		ErrorInfo info = new ErrorInfo("115", razorpayException.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

	/*
	 * This Method is to handle general exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handleGlobalException(Exception exception) {
		ErrorInfo info = new ErrorInfo("116", exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}