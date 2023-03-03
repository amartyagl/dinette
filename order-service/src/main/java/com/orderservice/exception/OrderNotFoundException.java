package com.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	
	public String getMessage() {
		return message;	
	}
	
	public OrderNotFoundException(String message) {
		super();
		this.message = message;
	}
}
