package com.hbc.api;

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
import com.hbc.dto.UserResponseDto;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.service.TournamentLocationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/tournament-location")
public class TourLocationApi {

	@Autowired
	TournamentLocationService tournamentLocationService;
	
	@GetMapping("/list")
	public ResponseEntity<?> doGetList(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok(tournamentLocationService.findAll(page, size));
	}
	
	@GetMapping
	public ResponseEntity<?> doGetByTourId(@RequestParam("tourid") long tourId) {
		try {
			TourLocationDto result = tournamentLocationService.findByTourId(tourId);
			return ResponseEntity.ok(result);
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("404", "TourId not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> doPostOne(@RequestBody TourLocationDto tourLocationDto,
			HttpSession session) {
		try {
			tournamentLocationService.save(tourLocationDto,
					(UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> doPutOne(@RequestBody TourLocationDto tourLocationDto,
			HttpSession session) {
		try {
			tournamentLocationService.update(tourLocationDto,
					(UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER));
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> doDelete(@PathVariable Long id) {
		try {
			tournamentLocationService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception ex) {
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
