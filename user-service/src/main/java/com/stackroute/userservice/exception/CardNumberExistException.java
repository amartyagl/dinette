package com.stackroute.userservice.exception;

public class CardNumberExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public CardNumberExistException(String message) {
		super(message);
	}

}
