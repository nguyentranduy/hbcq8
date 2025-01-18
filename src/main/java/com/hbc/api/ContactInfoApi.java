package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.service.ContactInfoService;

@RestController
@RequestMapping("/api/v1/contact-info")
public class ContactInfoApi {

	@Autowired
	ContactInfoService aontactInfoService;

	@GetMapping
	public ResponseEntity<?> doGet() {
		return ResponseEntity.ok(aontactInfoService.doGet());
	}
}
