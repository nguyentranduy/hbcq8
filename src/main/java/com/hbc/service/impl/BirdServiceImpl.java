package com.hbc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.entity.Bird;
import com.hbc.repo.BirdRepo;
import com.hbc.service.BirdService;

@Service
public class BirdServiceImpl implements BirdService {
	
	@Autowired
	BirdRepo repo;

	@Override
	public List<BirdResponseDto> doGetBirds(long userId) {
		List<Bird> birds = repo.findByUserId(userId);
		List<BirdResponseDto> birdResponseDtos = new ArrayList<>();
		for (Bird bird : birds) {
			birdResponseDtos.add(BirdResponseDto.build(bird));
		}
		return birdResponseDtos;
	}
}
