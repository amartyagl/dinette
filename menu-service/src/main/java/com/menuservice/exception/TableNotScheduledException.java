package com.menuservice.exception;

public class TableNotScheduledException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public TableNotScheduledException(String message) {
		super();
		this.message = message;
	}

}
