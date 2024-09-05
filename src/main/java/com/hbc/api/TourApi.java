package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.TournamentService;

@RestController
@RequestMapping("/api/v1/tournament")
public class TourApi {
	
	@Autowired
	TournamentService tournamentService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(tournamentService.findAllAvailable());
	}
}
