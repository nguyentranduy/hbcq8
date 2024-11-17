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
import com.hbc.dto.tournament.AdminTourApproveDto;
import com.hbc.dto.tournament.AdminTourRejectDto;
import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.tournament.CannotDeleteTourActivedException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.exception.tournament.TourNotFoundException;
import com.hbc.service.TournamentDetailService;
import com.hbc.service.TournamentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/tournament")
public class AdminTourApi {

	@Autowired
	TournamentService tournamentService;

	@Autowired
	TournamentDetailService tourDetailService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(tournamentService.findAllAvailable());
	}

	@GetMapping("/{tourId}")
	public ResponseEntity<?> doGetByTourId(@PathVariable("tourId") long tourId) {
		try {
			return ResponseEntity.ok(tournamentService.findById(tourId));
		} catch (TourNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse("400", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PutMapping("/{tourId}")
	public ResponseEntity<?> doUpdate(@PathVariable("tourId") long tourId,
			@RequestBody TourRequestDto requestDto, HttpSession session) {
		try {
			TourResponseDto response = tournamentService.doUpdate(tourId, requestDto,
					(UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.ok(response);
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (TourNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (TourInfoFailedException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@DeleteMapping("/{tourId}")
	public ResponseEntity<?> doDelete(@PathVariable("tourId") long tourId, HttpSession session) {
		try {
			tournamentService.doDelete(tourId, (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.ok().build();
		} catch (AuthenticationException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (TourNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (CannotDeleteTourActivedException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@GetMapping("/approve")
	public ResponseEntity<?> doGetApproveByTourId(@RequestParam("tourId") long tourId) {
		try {
			return ResponseEntity.ok(tourDetailService.findByTourIdForApprove(tourId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/approve-result")
	public ResponseEntity<?> doPostApprove(@RequestBody AdminTourApproveDto dto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			tourDetailService.doApprove(dto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/reject-result")
	public ResponseEntity<?> doPostReject(@RequestBody AdminTourRejectDto dto, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			tourDetailService.doReject(dto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	
	
	@GetMapping("/sort")
	public ResponseEntity<?> doGetSort(@RequestParam("tourId") long tourId) {
		try {
			tourDetailService.doSortRankByTourId(tourId);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
