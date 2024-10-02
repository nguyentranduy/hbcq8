package com.hbc.service;

import java.util.List;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.RegisterBirdRequestDto;

public interface BirdService {

	List<BirdResponseDto> doGetBirds(long userId);
	BirdResponseDto register(RegisterBirdRequestDto birdDto, Long currentUserId) throws Exception;
}
