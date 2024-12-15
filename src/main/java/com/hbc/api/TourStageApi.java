package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.TournamentStageService;

@RestController
@RequestMapping("/api/v1/tour-stage")
public class TourStageApi {

	@Autowired
	TournamentStageService tourStageService;

	@GetMapping("/status")
	public ResponseEntity<?> doGetList(@RequestParam("stageId") long stageId) {
		return ResponseEntity.ok(tourStageService.isFinished(stageId));
	}
}
