package com.hbc.exception.tournament;

public class TourNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4601014586415545195L;
	
	private final String errorCode;
	private final String errorMessage;

	public TourNotFoundException(String errorCode, String errorMessage) {
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
