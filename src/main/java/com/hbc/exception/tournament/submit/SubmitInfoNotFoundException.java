package com.hbc.exception.tournament.submit;

public class SubmitInfoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1131209755872588403L;
	private final String errorCode;
	private final String errorMessage;

	public SubmitInfoNotFoundException(String errorCode, String errorMessage) {
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
