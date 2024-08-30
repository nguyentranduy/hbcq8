package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.service.TournamentLocationService;

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
}
