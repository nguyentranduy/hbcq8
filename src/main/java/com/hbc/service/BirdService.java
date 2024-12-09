package com.hbc.service;

import java.util.List;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.BirdUpdateRequestDto;

public interface BirdService {

	List<BirdResponseDto> doGetBirds(long userId);
	BirdResponseDto update(BirdUpdateRequestDto birdDto, Long currentUserId) throws Exception;
}
