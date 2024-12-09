package com.hbc.exception.tournament.submit;

public class InvalidSubmitInfoException extends RuntimeException {

	private static final long serialVersionUID = -1962536517429880366L;

	private final String errorCode;
	private final String errorMessage;

	public InvalidSubmitInfoException(String errorCode, String errorMessage) {
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
