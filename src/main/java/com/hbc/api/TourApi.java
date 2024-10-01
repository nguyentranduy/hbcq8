package com.hbc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.tournament.TournamentInfoDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.service.TournamentInfoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/tour")
public class TourApi {

	@Autowired
	TournamentInfoService tourInfoService;

	@GetMapping("/list")
	public ResponseEntity<?> doGetList(HttpSession session) {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<TournamentInfoDto> response = tourInfoService.doGetList(currentUser.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
