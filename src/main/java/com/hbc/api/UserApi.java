package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateRequestDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.service.BirdService;
import com.hbc.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

	@Autowired
	UserService userService;
	
	@Autowired
	BirdService birdService;
	
	@PostMapping("/img")
	public ResponseEntity<?> doUpdateImgUrl(@RequestParam(value = "username") String username,
			@RequestPart("image") MultipartFile img) {
		try {
			userService.doUpdateImg(img, username);
			return ResponseEntity.ok("OK");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> doGetProfile(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		UserResponseDto response = userService.findById(currentUser.getId());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update")
	public ResponseEntity<?> doUpdateProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
			HttpSession session) {
		try {
			UserResponseDto responseDto = userService.doUpdate(userUpdateRequestDto, session);
			session.setAttribute(SessionConst.CURRENT_USER, responseDto);
			return ResponseEntity.ok(responseDto);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (DuplicatedUserException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (CustomException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
