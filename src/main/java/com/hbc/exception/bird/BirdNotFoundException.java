package com.hbc.exception.bird;

public class BirdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2511859911650708812L;
	
	private final String errorCode;
	private final String errorMessage;

	public BirdNotFoundException(String errorCode, String errorMessage) {
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
