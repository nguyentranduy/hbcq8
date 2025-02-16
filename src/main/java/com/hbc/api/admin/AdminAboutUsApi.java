package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.entity.AboutUs;
import com.hbc.service.AdminAboutUsService;

@RestController
@RequestMapping("/api/v1/admin/about-us")
public class AdminAboutUsApi {

	@Autowired
	AdminAboutUsService adminAboutUsService;
	
	@GetMapping("")
	public ResponseEntity<?> doGet() {
		return ResponseEntity.ok(adminAboutUsService.get());
	}
	
	@PostMapping("")
	public ResponseEntity<?> doPost(@RequestBody AboutUs aboutUs) {
		try {
			adminAboutUsService.insert(aboutUs);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
		}
	}
}
