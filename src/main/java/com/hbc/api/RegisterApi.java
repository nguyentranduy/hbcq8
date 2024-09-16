package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.UserRegisterRequestDto;
import com.hbc.dto.UserResponseDto;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin("*")
public class RegisterApi {
	
	@Autowired
	UserService userService;

	@PostMapping
	public ResponseEntity<?> doPostRegister(@RequestBody UserRegisterRequestDto requestDto, HttpSession session) {
		try {
			UserResponseDto responseDto = userService.doRegister(requestDto);
			session.setAttribute(SessionConst.CURRENT_USER, responseDto);
			return ResponseEntity.ok(requestDto);
		} catch (DuplicatedUserException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		}
	}
}
