package com.hbc.exception.user;

public class InvalidActionException extends RuntimeException {

	private static final long serialVersionUID = 2622215691725598344L;

	private final String errorCode;
	private final String errorMessage;

	public InvalidActionException(String errorCode, String errorMessage) {
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
