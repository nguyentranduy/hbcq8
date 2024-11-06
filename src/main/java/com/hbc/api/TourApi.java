package com.hbc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tournament.TournamentInfoDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.service.TournamentDetailService;
import com.hbc.service.TournamentInfoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/tour")
public class TourApi {

	@Autowired
	TournamentInfoService tourInfoService;

	@Autowired
	TournamentDetailService tourDetailService;

	@GetMapping("/list")
	public ResponseEntity<?> doGetList(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetList(currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> doGetListMe(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetListMe(currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	// /api/v1/tour/detail?tourId={tourId}
	@GetMapping("/detail")
	public ResponseEntity<?> doGetDetail(@RequestParam("tourId") long tourId, HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TourDetailResponseDto> response = tourDetailService.findByTourIdAndUserId(tourId, currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
