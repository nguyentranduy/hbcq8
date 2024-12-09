package com.hbc.service;

import java.util.List;

import com.hbc.dto.post.PostResponseDto;

public interface PostService {

	PostResponseDto findBySlug(String slug);
	List<PostResponseDto> findAllAvailable();
}
