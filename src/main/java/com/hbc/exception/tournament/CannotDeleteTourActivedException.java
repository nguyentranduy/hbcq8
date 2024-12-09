package com.hbc.exception.tournament;

public class CannotDeleteTourActivedException extends RuntimeException {

	private static final long serialVersionUID = -6229045505350013884L;

	private final String errorCode;
	private final String errorMessage;

	public CannotDeleteTourActivedException(String errorCode, String errorMessage) {
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
