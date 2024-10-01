package com.hbc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.tournament.TournamentInfoDto;
import com.hbc.service.TournamentInfoService;

@RestController
@RequestMapping("/api/v1/tour")
public class TourApi {

	@Autowired
	TournamentInfoService tourInfoService;

	@GetMapping("/list")
	public ResponseEntity<?> doGetList() {
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetList();
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
