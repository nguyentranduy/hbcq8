package com.hbc.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.googledrive.GoogleDriveFileDTO;
import com.hbc.service.GoogleDriveFileService;

@RestController
@RequestMapping("/api/v1/admin/ggdrive")
public class AdminGoogleDriveApi {

	@Autowired
	GoogleDriveFileService googleDriveFileService;

	@GetMapping
	public ResponseEntity<List<GoogleDriveFileDTO>> findAll() {
		return ResponseEntity.ok(googleDriveFileService.findAll());
	}

	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String upload(@RequestParam("file") MultipartFile file) {
		return googleDriveFileService.upload(file);
	}
}
