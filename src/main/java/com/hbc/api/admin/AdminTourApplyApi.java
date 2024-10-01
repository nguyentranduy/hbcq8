package com.hbc.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.dto.tourapply.admin.AdminTourApplyInfoDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyUpdateDto;
import com.hbc.exception.tourapply.TourApplyException;
import com.hbc.exception.tourapply.TourApplyNotFoundException;
import com.hbc.service.TournamentApplyService;

@RestController
@RequestMapping("/api/v1/admin/tour-apply")
public class AdminTourApplyApi {
	
	@Autowired
	TournamentApplyService tournamentApplyService;

	@GetMapping("/{tourId}")
	public ResponseEntity<?> doGetList(@PathVariable("tourId") long tourId) {
		try {
			List<AdminTourApplyInfoDto> response = tournamentApplyService.findByTourId(tourId);
			return ResponseEntity.ok(response);
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse("404", "Giải đua không tồn tại.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
		}
	}

	@PostMapping("/approve")
	public ResponseEntity<?> doPostApprove(@RequestBody AdminTourApplyUpdateDto dto) {
		try {
			tournamentApplyService.doApprove(dto);
			return ResponseEntity.ok().build();
		} catch (TourApplyException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (TourApplyNotFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
		}
	}
}
