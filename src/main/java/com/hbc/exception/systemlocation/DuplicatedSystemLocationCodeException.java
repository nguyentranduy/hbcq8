package com.hbc.exception.systemlocation;

public class DuplicatedSystemLocationCodeException extends RuntimeException {

	private static final long serialVersionUID = -5353343413433677222L;

	private final String errorCode;
	private final String errorMessage;

	public DuplicatedSystemLocationCodeException(String errorCode, String errorMessage) {
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
