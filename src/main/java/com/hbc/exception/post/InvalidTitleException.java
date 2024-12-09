package com.hbc.exception.post;

public class InvalidTitleException extends RuntimeException {

	private static final long serialVersionUID = -4419108798734802352L;

	private final String errorCode;
	private final String errorMessage;

	public InvalidTitleException(String errorCode, String errorMessage) {
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
