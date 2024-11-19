package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.service.TournamentStageService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/tour-stage")
public class AdminTourStageApi {

	@Autowired
	TournamentStageService tourStageService;

	@GetMapping("/active")
	public ResponseEntity<?> doActive(@RequestParam("id") long stageId, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
			tourStageService.updateStatus(stageId, true, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}

	@GetMapping("/deactive")
	public ResponseEntity<?> doDeactive(@RequestParam("id") long stageId, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
			tourStageService.updateStatus(stageId, false, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
