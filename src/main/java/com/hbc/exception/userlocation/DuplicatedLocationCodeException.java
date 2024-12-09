package com.hbc.exception.userlocation;

public class DuplicatedLocationCodeException extends RuntimeException {
	
	private static final long serialVersionUID = -5704373812041174895L;

	private final String errorCode;
	private final String errorMessage;

	public DuplicatedLocationCodeException(String errorCode, String errorMessage) {
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
