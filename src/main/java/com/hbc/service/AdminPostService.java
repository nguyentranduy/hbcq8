package com.hbc.service;

import java.util.List;

import com.hbc.dto.post.AdminPostResponseDto;

public interface AdminPostService {

	List<AdminPostResponseDto> findAllAvailable();
}
