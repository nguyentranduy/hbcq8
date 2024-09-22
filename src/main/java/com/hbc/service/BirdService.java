package com.hbc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.entity.Bird;
import com.hbc.exception.bird.DuplicatedBirdException;
import com.hbc.exception.bird.KeyConstraintBirdException;

public interface BirdService {
	Boolean doDelete(Long id) throws KeyConstraintBirdException;

	List<Bird> doGetBirds(Long userId);

	BirdResponseDto doGetBird(String birdSecretKey);

	BirdResponseDto doInsert(BirdResponseDto dto) throws DuplicatedBirdException;

	BirdResponseDto doUpdate(BirdResponseDto dto);

	String doUpdateImg(MultipartFile file, Long id);
}
