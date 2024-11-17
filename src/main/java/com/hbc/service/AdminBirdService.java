package com.hbc.service;

import java.util.List;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.admin.AdminBirdRequestDto;
import com.hbc.dto.bird.admin.AdminBirdUpdateRequestDto;

public interface AdminBirdService {

	List<BirdResponseDto> doGetBirds(long userId);
	BirdResponseDto register(AdminBirdRequestDto birdDto, Long currentUserId) throws Exception;
	BirdResponseDto update(AdminBirdUpdateRequestDto birdDto, Long currentUserId) throws Exception;
	void delete(String code, long currentUserId) throws Exception;
}
