package com.paymentservice.exception;

public class PaymentNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public PaymentNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	
}


