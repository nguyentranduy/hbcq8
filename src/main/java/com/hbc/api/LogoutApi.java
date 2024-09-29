package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.entity.LoginManagement;
import com.hbc.service.LoginManagementService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/logout")
public class LogoutApi {
	
	@Autowired
	LoginManagementService loginManagementService;

	@GetMapping()
	public ResponseEntity<?> doLogout(HttpServletRequest req) {
		long userId = Long.parseLong(req.getHeader("userId"));
		String token = req.getHeader("token");
		LoginManagement loginManagement = loginManagementService.findByUserIdAndToken(userId, token);
		
		if (loginManagement != null) {
			loginManagementService.deactiveToken(userId, token);
			return ResponseEntity.ok().build();
		}
		
		ErrorResponse errorResponse = new ErrorResponse("400", "Bad request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
