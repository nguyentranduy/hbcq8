package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.AboutUsService;

@RestController
@RequestMapping("/api/v1/about-us")
public class AboutUsApi {

	@Autowired
	AboutUsService aboutUsService;
	
	@GetMapping("")
	public ResponseEntity<?> doGet() {
		return ResponseEntity.ok(aboutUsService.get());
	}
}
