package com.hbc.exception.systemlocation;

public class SystemLocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 802816813391392568L;

	private final String errorCode;
	private final String errorMessage;

	public SystemLocationNotFoundException(String errorCode, String errorMessage) {
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
