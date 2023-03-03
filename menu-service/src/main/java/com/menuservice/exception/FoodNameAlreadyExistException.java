package com.menuservice.exception;

public class FoodNameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public FoodNameAlreadyExistException(String message) {
		super();
		this.message = message;
	}

}
