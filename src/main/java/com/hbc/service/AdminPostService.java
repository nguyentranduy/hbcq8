package com.hbc.service;

import java.util.List;

import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.post.PostResponseDto;

public interface AdminPostService {

	List<PostResponseDto> findAllAvailable();
	void insert(AdminPostRequestDto requestDto, long currentUserId);
}
