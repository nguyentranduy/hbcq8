package com.hbc.validator;

import com.hbc.constant.SessionConst;
import com.hbc.dto.user.UserResponseDto;

import jakarta.servlet.http.HttpSession;

public class UserValidator {

	public static boolean canSelfUpdated(Long userRequestedId, HttpSession session) {
		if (session.getAttribute(SessionConst.CURRENT_USER) == null) {
			return false;
		}
		
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
			return userRequestedId.longValue() == currentUser.getId();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
