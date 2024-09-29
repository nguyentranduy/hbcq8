package com.hbc.validator;

import jakarta.servlet.http.HttpServletRequest;

public class UserValidator {
	
	private UserValidator() {
		
	}

	public static boolean canSelfUpdated(Long userRequestedId, HttpServletRequest req) {
		String userId = req.getHeader("userId");
		String token = req.getHeader("token");
        if (Long.parseLong(userId) != userRequestedId || token == null) {
        	return false;
        }
        return true;
	}
}
