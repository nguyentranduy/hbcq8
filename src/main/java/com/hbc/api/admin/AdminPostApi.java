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
import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.post.AdminPostRequestUpdateDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.post.InvalidTitleException;
import com.hbc.exception.post.PostNotFoundException;
import com.hbc.service.AdminPostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/post")
public class AdminPostApi {

	@Autowired
	AdminPostService adminPostService;

	@GetMapping
	public ResponseEntity<?> doGetAll() {
		return ResponseEntity.ok(adminPostService.findAllAvailable());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doGetById(@PathVariable("id") long id) {
		try {
			return ResponseEntity.ok(adminPostService.findById(id));
		} catch (PostNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> doDeleteById(@PathVariable("id") long id, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
			adminPostService.delete(id, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (PostNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> doPost(@RequestBody AdminPostRequestDto requestDto, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
			adminPostService.insert(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (InvalidTitleException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> doPut(@RequestBody AdminPostRequestUpdateDto requestDto, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_ADMIN);
			adminPostService.update(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (InvalidTitleException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
