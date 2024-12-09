package com.hbc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbc.dto.ErrorResponse;
import com.hbc.exception.post.PostNotFoundException;
import com.hbc.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostApi {

	@Autowired
	PostService postService;

	@GetMapping
	public ResponseEntity<?> doGetAll() {
		return ResponseEntity.ok(postService.findAllAvailable());
	}

	@GetMapping("/{slug}")
	public ResponseEntity<?> doGetBySlug(@PathVariable("slug") String slug) {
		try {
			return ResponseEntity.ok(postService.findBySlug(slug));
		} catch (PostNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
