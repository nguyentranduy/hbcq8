package com.hbc.api;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.constant.Globals;
import com.hbc.dto.ErrorResponse;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.entity.Bird;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.DuplicatedBirdException;
import com.hbc.exception.bird.KeyConstraintBirdException;
import com.hbc.gcp.service.GcpService;
import com.hbc.service.BirdService;

@RestController
@RequestMapping("api/v1/bird")
public class BirdApi {
	@Autowired
	BirdService birdService;

	@Autowired
	GcpService gcpService;

	@PostMapping("/insert")
	public ResponseEntity<?> doInsert(@RequestBody BirdResponseDto requestDto) {
		try {
			BirdResponseDto birdResponseDto = birdService.doInsert(requestDto);
			return ResponseEntity.ok(birdResponseDto);
		} catch (DuplicatedBirdException e) {
			// TODO: handle exception
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		} catch (CustomException e) {
			// TODO: handle exception
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

	}

	@GetMapping("get-bird/{birdSecretKey}")
	public ResponseEntity<?> doGetBird(@PathVariable("birdSecretKey") String birdSecretKey) {
		try {
			BirdResponseDto bird = birdService.doGetBird(birdSecretKey);
			return ResponseEntity.ok(bird);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

	}

	@GetMapping("get-user-birds/{userId}")
	public ResponseEntity<?> doGetBirds(@PathVariable(value = "userId") Long userId) {

		try {
			List<BirdResponseDto> list = birdService.doGetBirds(userId);
			return ResponseEntity.ok(list);
		} catch (AuthenticationException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> doDelete(@PathVariable(value = "id") Long id) {

		try {
			birdService.doDelete(id);
			return ResponseEntity.ok("Deleted");
		} catch (KeyConstraintBirdException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> doUpdate(@RequestBody BirdResponseDto dto) {
		try {
			BirdResponseDto birdResponseDto = birdService.doUpdate(dto);
			return ResponseEntity.ok(birdResponseDto);
		} catch (AuthenticationException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	@PutMapping("/update-avatar")
	public ResponseEntity<?> doUpdateAvatar(@RequestParam("birdSecretKey") String birdSecretKey,
			@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse("400", "File can't be empty.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		if (file.getSize() >= Globals.MAX_FILE_SIZE) {
			ErrorResponse errorResponse = new ErrorResponse("400", "File size cannot be lager than 5mb.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		if (!Globals.isImage(file.getContentType())) {
			ErrorResponse errorResponse = new ErrorResponse("400",
					"File is not an image. Allowed formats: JPEG, PNG, GIF.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		try {
			// TODO Delete old file after change
			File tempFile = File.createTempFile("temp", null);
			file.transferTo(tempFile);
			String imgUrl = gcpService.uploadImageToDrive(tempFile, birdSecretKey);
			birdService.doUpdateImg(birdSecretKey, imgUrl);
			return ResponseEntity.ok(imgUrl);
		} catch (IOException e) {
			ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		} catch (GeneralSecurityException e) {
			ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		} catch (AuthenticationException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (CustomException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

	}

}
