package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;
import com.hbc.exception.tourapply.TourApplyNotFoundException;
import com.hbc.service.TournamentApplyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/tour-apply")
public class TourApplyApi {

	@Autowired
	TournamentApplyService tournamentApplyService;

	@PostMapping
	public ResponseEntity<?> doPost(@RequestBody TourApplyRequestDto dto, HttpSession session) {
		try {
			TourApplyResponseDto response = tournamentApplyService.doRegister(dto, session);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (TourApplyException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (CustomException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@GetMapping("/cancel")
	public ResponseEntity<?> cancel(@RequestParam("tourId") long tourId, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			tournamentApplyService.doCancel(tourId, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (TourApplyException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
