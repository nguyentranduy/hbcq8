package com.hbc.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.constant.Globals;
import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateRequestDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.gcp.service.GcpService;
import com.hbc.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

	@Autowired
	UserService userService;

	@Autowired
	GcpService gcpService;

	@PostMapping("/update-avatar")
	public ResponseEntity<?> doUpdateImgUrl(@RequestParam("username") String username,
			@RequestParam("image") MultipartFile file) {
		if (file.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse("400", "File cannot be empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		if (file.getSize() >= Globals.MAX_FILE_SIZE) {
			ErrorResponse errorResponse = new ErrorResponse("400", "File size cannot be larger than 5mb.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		String contentType = file.getContentType();
		if (!Globals.isImage(contentType)) {
			ErrorResponse errorResponse = new ErrorResponse("400",
					"File is not an image. Allowed formats: JPEG, PNG, GIF.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			File tempFile = File.createTempFile("temp", null);
			file.transferTo(tempFile);
			String imgUrl = gcpService.uploadImageToDrive(tempFile, username);
			userService.doUpdateImg(imgUrl, username);
			return ResponseEntity.ok(imgUrl);
		} catch (IOException e) {
			ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		} catch (GeneralSecurityException e) {
			ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		} catch (AuthenticationException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> doGetProfile(@PathVariable("username") String username) {
		// TODO
		return null;
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
		} catch (CustomException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
