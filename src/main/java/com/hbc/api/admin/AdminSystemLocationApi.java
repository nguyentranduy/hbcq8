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
import com.hbc.dto.systemlocation.SystemLocationRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.systemlocation.DuplicatedSystemLocationCodeException;
import com.hbc.exception.systemlocation.InvalidSystemLocationCodeException;
import com.hbc.exception.systemlocation.SystemLocationNotFoundException;
import com.hbc.service.AdminSystemLocationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/system-location")
public class AdminSystemLocationApi {

	@Autowired
	AdminSystemLocationService adminSystemLocationService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(adminSystemLocationService.findAllAvailable());
	}

	@GetMapping("/{code}")
	public ResponseEntity<?> doGetByCode(@PathVariable("code") String code) {
		try {
			return ResponseEntity.ok(adminSystemLocationService.findByCode(code));
		} catch (SystemLocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody SystemLocationRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminSystemLocationService.doRegister(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (DuplicatedSystemLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (InvalidSystemLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> doUpdate(@PathVariable("id") long systemLocationId,
			@RequestBody SystemLocationRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminSystemLocationService.doUpdate(systemLocationId, requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (SystemLocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (DuplicatedSystemLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (InvalidSystemLocationCodeException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> doDelete(@PathVariable("id") long systemLocationId, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
		try {
			adminSystemLocationService.doDelete(systemLocationId, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (SystemLocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
