package com.hbc.constant;

import java.util.List;

public class TourDetailStatusCodeConst {
	public static final String STATUS_CODE_REJECTED = "R";
	public static final String STATUS_CODE_APPROVED = "A";
	public static final String STATUS_CODE_WAITING = "W";
	public static final List<String> LIST_STATUS_CODE = List.of(STATUS_CODE_REJECTED, STATUS_CODE_APPROVED, STATUS_CODE_WAITING);
}
