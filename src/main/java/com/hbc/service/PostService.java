package com.hbc.service;

import com.hbc.dto.post.PostResponseDto;

public interface PostService {

	PostResponseDto findBySlug(String slug);
}
