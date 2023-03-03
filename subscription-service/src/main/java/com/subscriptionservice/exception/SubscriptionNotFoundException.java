package com.subscriptionservice.exception;

public class SubscriptionNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

private String message;
	
	public String getMessage() {
		return message;	
	}
	
	public SubscriptionNotFoundException(String message) {
		super();
		this.message = message;
	}
}
