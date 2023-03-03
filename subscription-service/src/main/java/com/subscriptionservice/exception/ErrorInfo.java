package com.subscriptionservice.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ErrorInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	private LocalDateTime time;

	public ErrorInfo(String errorCode, String errorMessage, LocalDateTime time) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.time = time;
	}
}
