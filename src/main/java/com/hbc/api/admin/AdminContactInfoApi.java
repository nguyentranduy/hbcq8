package com.hbc.api.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.contactinfo.UpdateContactRequestDto;
import com.hbc.service.ContactInfoService;

@RestController
@RequestMapping("/api/v1/admin/contact-info")
public class AdminContactInfoApi {

	@Autowired
	ContactInfoService contactInfoService;

	@PutMapping
	public ResponseEntity<?> doPut(@RequestBody UpdateContactRequestDto dto) {
		try {
			contactInfoService.doUpdate(dto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

	@GetMapping
	public ResponseEntity<?> doGet() {
		return ResponseEntity.ok(contactInfoService.doGet());
	}
}
