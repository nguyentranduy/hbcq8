package com.hbc.exception.tournament.submit;

public class OutOfTimeException extends RuntimeException {

	private static final long serialVersionUID = 8803950274648318922L;

	private final String errorCode;
	private final String errorMessage;

	public OutOfTimeException(String errorCode, String errorMessage) {
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
