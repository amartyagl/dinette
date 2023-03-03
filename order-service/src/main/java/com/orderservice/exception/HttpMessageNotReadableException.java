package com.orderservice.exception;

public class HttpMessageNotReadableException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
private String message;
	
	public String getMessage() {
		return message;	
	}
	
	public HttpMessageNotReadableException(String message) {
		super();
		this.message = message;
	}

}
