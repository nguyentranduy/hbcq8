package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.constant.SessionConst;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.service.AdminPostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/admin/post")
public class AdminPostApi {

	@Autowired
	AdminPostService adminPostService;
	
	@GetMapping
	public ResponseEntity<?> doGetAll() {
		return ResponseEntity.ok(adminPostService.findAllAvailable());
	}
	
	@PostMapping
	public ResponseEntity<?> doPost(@RequestBody AdminPostRequestDto requestDto, HttpSession session) {
		try {
			UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);
			adminPostService.insert(requestDto, currentUser.getId());
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
}
