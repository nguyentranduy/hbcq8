package com.hbc.api.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.service.TournamentLocationService;

@RestController
@RequestMapping("/api/v1/tournament-location")
public class TourLocationApi {

	@Autowired
	TournamentLocationService tournamentLocationService;
	
	@GetMapping
	public ResponseEntity<?> doGetByTourId(@RequestParam("tourid") long tourId) {
		TourLocationDto result = tournamentLocationService.findByTourId(tourId);
		return ResponseEntity.ok(result);
	}
}
