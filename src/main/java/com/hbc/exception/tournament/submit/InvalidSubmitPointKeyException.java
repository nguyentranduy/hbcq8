package com.hbc.exception.tournament.submit;

public class InvalidSubmitPointKeyException extends RuntimeException {

	private static final long serialVersionUID = -1528356832273981096L;

	private final String errorCode;
	private final String errorMessage;

	public InvalidSubmitPointKeyException(String errorCode, String errorMessage) {
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
