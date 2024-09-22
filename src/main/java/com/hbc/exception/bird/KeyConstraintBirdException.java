package com.hbc.exception.bird;

public class KeyConstraintBirdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6046740220945530231L;
	private final String errorCode;
	private final String errorMessage;

	public KeyConstraintBirdException(String errorCode, String errorMessage) {
		super();
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
