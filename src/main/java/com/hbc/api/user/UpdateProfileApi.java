package com.hbc.api.user;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.constant.Globals;
import com.hbc.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UpdateProfileApi {
	@Autowired
	UserService userService;

	@PostMapping("/bachhoa/api/upload")
	public ResponseEntity<String> uploadSanPhamWithImage(@RequestParam(value = "username") String username,
			@RequestPart("image") MultipartFile img) throws IOException {
		try {
			String imgName = "avatar_" + username;
			String filePath = Globals.FOLDER_PATH + imgName;
			img.transferTo(new File(filePath));
			String imgUrl = Globals.HOST + Globals.PORT + imgName;
			userService.doUpdateImg(imgUrl, username);
			return ResponseEntity.ok(imgUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
		}
	}

}
