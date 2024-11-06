package com.hbc.api.admin;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.dto.tourapply.admin.AdminTourApplyApproveDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyInfoDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyRejectDto;
import com.hbc.dto.tourlocation.CalDistanceRequestDto;
import com.hbc.dto.tourlocation.CalDistanceResponseDto;
import com.hbc.exception.calculatedistance.InvalidCoorFormatException;
import com.hbc.exception.tourapply.TourApplyNotFoundException;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.service.TournamentApplyService;
import com.hbc.service.impl.CalculateDistanceService;

@RestController
@RequestMapping("/api/v1/admin/tour-apply")
public class AdminTourApplyApi {

	@Autowired
	TournamentApplyService tournamentApplyService;

	@Autowired
	CalculateDistanceService calculateDistanceService;

	@GetMapping("/{tourId}")
	public ResponseEntity<?> doGetList(@PathVariable("tourId") long tourId) {
		try {
			List<AdminTourApplyInfoDto> response = tournamentApplyService.findByTourId(tourId);
			return ResponseEntity.ok(response);
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse("404", "Giải đua không tồn tại.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	// /api/v1/admin/tour-apply/detail?tourId={tourId}&requesterId={requesterId}
	@GetMapping("/detail")
	public ResponseEntity<?> doGetDetail(@RequestParam("tourId") long tourId,
			@RequestParam("requesterId") long requesterId) {
		try {
			List<AdminTourApplyInfoDto> response = tournamentApplyService.findByTourIdAndRequesterId(tourId, requesterId);
			return ResponseEntity.ok(response);
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse("404", "Giải đua không tồn tại.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PutMapping("/approve")
	public ResponseEntity<?> doPutApprove(@RequestBody AdminTourApplyApproveDto dto) {
		try {
			tournamentApplyService.doApprove(dto);
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (LocationNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}  catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PutMapping("/reject")
	public ResponseEntity<?> doPutReject(@RequestBody AdminTourApplyRejectDto dto) {
		try {
			tournamentApplyService.doReject(dto);
			return ResponseEntity.ok().build();
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}  catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@PostMapping("/calculate-distance")
	public ResponseEntity<?> calculateDistance(@RequestBody CalDistanceRequestDto calDistanceRequestDto) {
		try {
			CalDistanceResponseDto response = calculateDistanceService.calculateDistance(calDistanceRequestDto);
			return ResponseEntity.ok(response);
		} catch (InvalidCoorFormatException ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
