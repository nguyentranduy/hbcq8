package com.hbc.api;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.entity.Bird;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.DuplicatedBirdException;
import com.hbc.exception.bird.KeyConstraintBirdException;
import com.hbc.service.BirdService;

@RestController
@RequestMapping("api/v1/bird")
public class BirdApi {
	@Autowired
	BirdService birdService;

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

	@GetMapping("get-birds/{userId}")
	public ResponseEntity<?> doGetBirds(@PathVariable(value = "userId") Long userId) {

		try {
			List<Bird> list = birdService.doGetBirds(userId);
			List<BirdResponseDto> listDto = new ArrayList<>();
			for (Bird bird : list) {
				listDto.add(BirdResponseDto.build(bird));
			}
			return ResponseEntity.ok(listDto);
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

}
