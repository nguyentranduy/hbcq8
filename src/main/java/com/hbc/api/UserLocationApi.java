package com.hbc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.userlocation.UserLocationResponseDto;
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
}
