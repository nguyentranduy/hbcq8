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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.userlocation.UserLocationAdminRequestDto;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.exception.userlocation.DuplicatedLocationCodeException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.service.AdminUserLocationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/user-location")
public class AdminUserLocationApi {

	@Autowired
	AdminUserLocationService adminUserLocationService;

	// /api/v1/admin/user-location?user={userId}
	@GetMapping
	public ResponseEntity<?> doGetByUserId(@RequestParam("user") long userId) {
		return ResponseEntity.ok(adminUserLocationService.findByUserId(userId));
	}

	@GetMapping("/{code}")
	public ResponseEntity<?> doGetByCode(@PathVariable("code") String code) {
		try {
			return ResponseEntity.ok(adminUserLocationService.findByCode(code));
		} catch (LocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody UserLocationAdminRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminUserLocationService.doRegister(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (DuplicatedLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (InvalidCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> doUpdate(@PathVariable("id") long userLocationId,
			@RequestBody UserLocationAdminRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminUserLocationService.doUpdate(userLocationId, requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (LocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (DuplicatedLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (InvalidCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<?> doDelete(@PathVariable("code") String code, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminUserLocationService.doDelete(code, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (LocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
