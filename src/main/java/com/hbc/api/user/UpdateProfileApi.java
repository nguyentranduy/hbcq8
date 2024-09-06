package com.hbc.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.UserResponseDto;
import com.hbc.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UpdateProfileApi {
	
	@Autowired
	UserService userService;

	@PostMapping("/img")
	public ResponseEntity<?> doUpdateImgUrl(@RequestParam(value = "username") String username,
			@RequestPart("image") MultipartFile img) {
		try {
			userService.doUpdateImg(img, username);
			return ResponseEntity.ok("OK");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> doUpdateProfile(@RequestBody UserResponseDto userResponseDto) {
		try {
			UserResponseDto responseDto = userService.doUpdate(userResponseDto);
			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");

		}
	}
}
