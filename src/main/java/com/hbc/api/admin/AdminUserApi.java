package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.UserService;

@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserApi {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<?> doGetList() {
		return ResponseEntity.ok(userService.findAllAvailable());
	}
}
