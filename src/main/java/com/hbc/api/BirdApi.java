package com.hbc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.service.BirdService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/bird")
public class BirdApi {

	@Autowired
	BirdService birdService;

	@GetMapping("/me")
	public ResponseEntity<?> doGetMyBirds(HttpSession session) {
		UserResponseDto currentDto = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
		try {
			List<BirdResponseDto> response = birdService.doGetBirds(currentDto.getId());
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
