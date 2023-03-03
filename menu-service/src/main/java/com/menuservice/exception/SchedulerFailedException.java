package com.menuservice.exception;

public class SchedulerFailedException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public SchedulerFailedException(String message) {
		super();
		this.message = message;
	}

}
