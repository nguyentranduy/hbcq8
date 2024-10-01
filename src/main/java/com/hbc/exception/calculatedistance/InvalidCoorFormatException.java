package com.hbc.exception.calculatedistance;

public class InvalidCoorFormatException extends RuntimeException {

	private static final long serialVersionUID = 2511859911650708812L;
	
	private final String errorCode;
	private final String errorMessage;

	public InvalidCoorFormatException(String errorCode, String errorMessage) {
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
