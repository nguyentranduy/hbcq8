package com.hbc.service;

import java.util.List;

import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.post.AdminPostResponseDto;

public interface AdminPostService {

	List<AdminPostResponseDto> findAllAvailable();
	void insert(AdminPostRequestDto requestDto, long currentUserId);
}
