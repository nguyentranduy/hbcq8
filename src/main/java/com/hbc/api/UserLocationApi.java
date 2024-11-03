package com.hbc.api;

import java.util.List;

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
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.userlocation.UserLocationRequestDto;
import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.exception.userlocation.DuplicatedLocationCodeException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.service.UserLocationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user-location")
public class UserLocationApi {

	@Autowired
	UserLocationService userLocationService;

	@GetMapping("/me")
	public ResponseEntity<?> doGetAll(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		List<UserLocationResponseDto> response = userLocationService.findByUserId(currentUser.getId());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") long id, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			UserLocationResponseDto response = userLocationService.findById(id, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (LocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PostMapping
	public ResponseEntity<?> doPostOne(@RequestBody UserLocationRequestDto requestDto,
			HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			userLocationService.doRegister(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
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
	public ResponseEntity<?> doPutOne(@PathVariable("id") long locationId,
			@RequestBody UserLocationRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			userLocationService.doUpdate(locationId, requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (LocationNotFoundException ex) {
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

	@DeleteMapping("/{id}")
	public ResponseEntity<?> doDelete(@PathVariable("id") long locationId, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			userLocationService.doDelete(locationId, currentUser.getId());
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
