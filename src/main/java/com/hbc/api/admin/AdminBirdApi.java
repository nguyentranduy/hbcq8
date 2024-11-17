package com.hbc.api.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.admin.AdminBirdRequestDto;
import com.hbc.dto.bird.admin.AdminBirdUpdateRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.BirdAlreadyRequestedException;
import com.hbc.exception.bird.BirdNotFoundException;
import com.hbc.exception.bird.DuplicatedBirdInfoException;
import com.hbc.service.AdminBirdService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/bird")
public class AdminBirdApi {

	@Autowired
	AdminBirdService adminBirdService;

	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody AdminBirdRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			BirdResponseDto response = adminBirdService.register(requestDto, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (DuplicatedBirdInfoException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	// /api/v1/admin/bird?userId=?
	@GetMapping
	public ResponseEntity<?> doGetBirdsByUserId(@RequestParam("userId") long userId) {
		try {
			List<BirdResponseDto> response = adminBirdService.doGetBirds(userId);
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PutMapping
	public ResponseEntity<?> doUpdate(@RequestBody AdminBirdUpdateRequestDto requestDto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			BirdResponseDto response = adminBirdService.update(requestDto, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (DuplicatedBirdInfoException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<?> doDelete(@PathVariable("code") String code, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			adminBirdService.delete(code, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (BirdNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (BirdAlreadyRequestedException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}
}
