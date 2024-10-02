package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.RegisterBirdRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.DuplicatedBirdInfoException;
import com.hbc.service.BirdService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/bird")
public class BirdApi {

	@Autowired
	BirdService birdService;
	
	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody RegisterBirdRequestDto requestDto,
			HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			BirdResponseDto response = birdService.register(requestDto, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (DuplicatedBirdInfoException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
		}
	}
}
