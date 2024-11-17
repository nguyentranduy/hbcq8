package com.hbc.exception.systemlocation;

public class InvalidSystemLocationCodeException extends RuntimeException {

	private static final long serialVersionUID = 4278607888138692766L;

	private final String errorCode;
	private final String errorMessage;

	public InvalidSystemLocationCodeException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
