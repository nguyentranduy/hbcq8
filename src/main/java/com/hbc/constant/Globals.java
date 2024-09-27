package com.hbc.constant;

public class Globals {
	public static final int MAX_FILE_SIZE = 5 * 1024 * 1024;

	public static final boolean isImage(String contentType) {
		return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png")
				|| contentType.equals("image/gif"));
	}
}
