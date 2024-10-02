package com.hbc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.RegisterBirdRequestDto;
import com.hbc.entity.Bird;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.DuplicatedBirdInfoException;
import com.hbc.repo.BirdRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.BirdService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class BirdServiceImpl implements BirdService {
	
	@Autowired
	BirdRepo birdRepo;
	
	@Autowired
	UserRepo userRepo;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<BirdResponseDto> doGetBirds(long userId) {
		List<Bird> birds = birdRepo.findByUserId(userId);
		List<BirdResponseDto> birdResponseDtos = new ArrayList<>();
		for (Bird bird : birds) {
			birdResponseDtos.add(BirdResponseDto.build(bird));
		}
		return birdResponseDtos;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BirdResponseDto register(RegisterBirdRequestDto birdDto, Long currentUserId) throws Exception {
		if (!StringUtils.hasText(birdDto.getCode())) {
			throw new CustomException("400", "Mã kiềng không được để trống.");
		}
		
		if (!StringUtils.hasText(birdDto.getName())) {
			throw new CustomException("400", "Tên chim không được để trống.");
		}
		
		if (birdRepo.existsByCode(birdDto.getCode())) {
			throw new DuplicatedBirdInfoException("400", "Mã kiềng đã tồn tại.");
		}

		try {
			birdRepo.doRegister(birdDto.getName(), birdDto.getCode(), birdDto.getUserId(), birdDto.getImgUrl(), currentUserId);
			entityManager.clear();
			Bird birdInserted = birdRepo.findByCode(birdDto.getCode());
			if (ObjectUtils.isEmpty(birdInserted)) {
				throw new Exception();
			}
			return BirdResponseDto.build(birdInserted);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
