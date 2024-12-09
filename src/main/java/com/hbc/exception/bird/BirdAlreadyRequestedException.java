package com.hbc.exception.bird;

public class BirdAlreadyRequestedException extends RuntimeException {

	private static final long serialVersionUID = 8603776662567668804L;

	private final String errorCode;
	private final String errorMessage;

	public BirdAlreadyRequestedException(String errorCode, String errorMessage) {
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
