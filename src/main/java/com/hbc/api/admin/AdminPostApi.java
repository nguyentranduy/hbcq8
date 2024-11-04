package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.AdminPostService;

@RestController
@RequestMapping("/api/v1/admin/post")
public class AdminPostApi {

	@Autowired
	AdminPostService adminPostService;
	
	@GetMapping
	public ResponseEntity<?> doGetAll() {
		return ResponseEntity.ok(adminPostService.findAllAvailable());
	}
}
