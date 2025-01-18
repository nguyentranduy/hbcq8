package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserLoginRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/login")
public class AdminLoginApi {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<?> doPostLogin(@RequestBody UserLoginRequestDto requestDto,
			HttpSession session) {
		try {
			UserResponseDto response = userService.doLogin(requestDto.getUsername(), requestDto.getPassword());
			session.setAttribute(SessionConst.CURRENT_ADMIN, response);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}
}
