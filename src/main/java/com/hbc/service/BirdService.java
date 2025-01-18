package com.hbc.service;

import java.util.List;

import com.hbc.dto.bird.BirdResponseDto;

public interface BirdService {

	List<BirdResponseDto> doGetBirds(long userId);
}
