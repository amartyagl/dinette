package com.subscriptionservice.exception;

public class SubscriptionAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String message;
	
	public String getMessage() {
		return message;	
	}
	
	public SubscriptionAlreadyExistException(String message) {
		super();
		this.message = message;
	}
}

