package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.ResetPasswordRequestDto;
import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateAdminRequestDto;
import com.hbc.exception.CustomException;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.exception.user.InvalidActionException;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.service.AdminUserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserApi {

	@Autowired
	AdminUserService adminUserService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(adminUserService.findAllAvailable());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") long id) {
		try {
			return ResponseEntity.ok(adminUserService.findByIdAvailable(id));
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody UserRegisterRequestDto requestDto, HttpSession session) {
		try {
			UserResponseDto responseDto = adminUserService.doRegister(requestDto);
			return ResponseEntity.ok(responseDto);
		} catch (DuplicatedUserException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (CustomException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> doUpdateProfile(@PathVariable("id") long id,
			@RequestBody UserUpdateAdminRequestDto userUpdateRequestDto, HttpSession session) {
		try {
			UserResponseDto responseDto = adminUserService.doUpdate(userUpdateRequestDto, session);
			return ResponseEntity.ok(responseDto);
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (DuplicatedUserException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
	
	@PutMapping("/reset-pw/{id}")
	public ResponseEntity<?> doResetPass(@PathVariable("id") long id,
			@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
		try {
			adminUserService.doResetPassword(resetPasswordRequestDto, id);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> doDelete(@PathVariable("id") long id, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
			adminUserService.deleteById(id, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (InvalidActionException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
