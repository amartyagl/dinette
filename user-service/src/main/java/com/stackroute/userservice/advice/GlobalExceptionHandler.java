package com.stackroute.userservice.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mongodb.MongoException;
import com.stackroute.userservice.exception.CardNumberExistException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

/*
 * @Slf4j annotation is for adding loggeer 
 * and @RestControllerAdvice is advice for RestController 
 * which help controller class to handle exception
 * */

@Slf4j
@RefreshScope
@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	Environment environment;

	/* This method is for handling validations exception on different attributes */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Error while validating input from user");
		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		return errorMap;
	}

	/*
	 * This method is for handling custom exception which will be thrown when user
	 * try to access data which not exist in database
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("User not exist in database");
		errorMap.put("errorMessage", environment.getProperty(exception.getMessage()));
		return errorMap;
	}

	/*
	 * This method is for handling custom exception which will be thrown when user
	 * try to add same data which exist in database
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UserAlreadyExistException.class)
	public Map<String, String> handleUserAlreadyExistException(UserAlreadyExistException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("User already exist it can not be added again");
		errorMap.put("errorMessage", environment.getProperty(exception.getMessage()));
		return errorMap;
	}

	/*
	 * This method is for handling custom exception which will be thrown when user
	 * try to add same card which exist in database
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(CardNumberExistException.class)
	public Map<String, String> handleCardNumberExistException(CardNumberExistException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Card already exist it can not be added again");
		errorMap.put("errorMessage", environment.getProperty(exception.getMessage()));
		return errorMap;
	}

	/*
	 * This method is for handling all the exception which will be raised from
	 * Exception class
	 */
	
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ExceptionHandler(MongoException.class)
	public Map<String, String> handleMongoException(MongoException exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Error while getting user from database {}", exception.getMessage());
		errorMap.put("errorMessage", exception.getMessage());
		return errorMap;
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleParentException(Exception exception) {
		Map<String, String> errorMap = new HashMap<>();
		log.error("Some error occurred {}", exception.getMessage());
		errorMap.put("errorMessage", exception.getMessage());
		return errorMap;
	}

}
