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
import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.tournament.UpdateTourRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.service.TournamentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/tournament")
public class AdminTourApi {
	
	@Autowired
	TournamentService tournamentService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(tournamentService.findAllAvailable());
	}
	
	@GetMapping("/{tourId}")
	public ResponseEntity<?> doGetByTourId(@PathVariable("tourId") long tourId) {
		try {
			return ResponseEntity.ok(tournamentService.findById(tourId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> doRegister(@RequestBody TourRequestDto requestDto, HttpSession session) {
		try {
			TourResponseDto response = tournamentService.doRegister(requestDto,
					(UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.ok(response);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (TourInfoFailedException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> doUpdate(@RequestBody UpdateTourRequestDto requestDto, HttpSession session) {
		try {
			TourResponseDto response = tournamentService.doUpdate(requestDto,
					(UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.ok(response);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (TourInfoFailedException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> doDelete(@RequestParam("id") long id, HttpSession session) {
		try {
			tournamentService.doDelete(id, (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.ok().build();
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
