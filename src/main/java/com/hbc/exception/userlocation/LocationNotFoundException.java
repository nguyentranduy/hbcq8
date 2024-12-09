package com.hbc.exception.userlocation;

public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4601014586415545195L;
	
	private final String errorCode;
	private final String errorMessage;

	public LocationNotFoundException(String errorCode, String errorMessage) {
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
