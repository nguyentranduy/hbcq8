package com.hbc.service;

import java.util.List;

import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.post.AdminPostRequestUpdateDto;
import com.hbc.dto.post.PostResponseDto;

public interface AdminPostService {

	List<PostResponseDto> findAllAvailable();
	void insert(AdminPostRequestDto requestDto, long currentUserId);
	void update(AdminPostRequestUpdateDto requestDto, long currentUserId) throws Exception;
	void delete(long id, long currentUserId) throws Exception;
	PostResponseDto findById(long id) throws Exception;
}
