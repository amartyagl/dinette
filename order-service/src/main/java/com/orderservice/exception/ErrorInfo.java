package com.orderservice.exception;

import java.io.Serializable; 
import java.time.LocalDateTime;

public class ErrorInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;
	private LocalDateTime time;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ErrorInfo(String errorCode, String errorMessage, LocalDateTime time) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.time = time;
	}
}
