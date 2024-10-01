package com.hbc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/logout")
public class LogoutApi {

	@GetMapping()
	public ResponseEntity<?> doLogout(HttpSession session) {
		if (session.getAttribute(SessionConst.CURRENT_USER) == null) {
			ErrorResponse errorResponse = new ErrorResponse("400", "Bad request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		session.removeAttribute(SessionConst.CURRENT_USER);
		return ResponseEntity.noContent().build();
	}
}
