package com.hbc.service;

import java.util.List;

import com.hbc.dto.bird.BirdRequestDto;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.BirdUpdateRequestDto;

public interface BirdService {

	List<BirdResponseDto> doGetBirds(long userId);
	BirdResponseDto register(BirdRequestDto birdDto, Long currentUserId) throws Exception;
	BirdResponseDto update(BirdUpdateRequestDto birdDto, Long currentUserId) throws Exception;
	void delete(String code, long currentUserId) throws Exception;
}
