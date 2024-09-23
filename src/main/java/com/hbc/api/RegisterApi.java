package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.CustomException;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterApi {
	
	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<?> doPostRegister(@RequestBody UserRegisterRequestDto requestDto, HttpSession session) {
		try {
			UserResponseDto responseDto = userService.doRegister(requestDto);
			session.setAttribute(SessionConst.CURRENT_USER, responseDto);
			return ResponseEntity.ok(responseDto);
		} catch (DuplicatedUserException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (CustomException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
