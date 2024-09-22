package com.hbc.exception.bird;

public class DuplicatedBirdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1720701507703399214L;

	private final String errorCode;
	private final String errorMessage;

	public DuplicatedBirdException(String errorCode, String errorMessage) {
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
