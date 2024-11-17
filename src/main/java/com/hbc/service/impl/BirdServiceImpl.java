package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.BirdUpdateRequestDto;
import com.hbc.entity.Bird;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.BirdNotFoundException;
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
		List<Bird> birds = birdRepo.findByUserIdAndIsDeleted(userId, false);
		List<BirdResponseDto> birdResponseDtos = new ArrayList<>();
		for (Bird bird : birds) {
			birdResponseDtos.add(BirdResponseDto.build(bird));
		}
		return birdResponseDtos;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BirdResponseDto update(BirdUpdateRequestDto birdDto, Long currentUserId) throws Exception {
		if (!ObjectUtils.isEmpty(currentUserId) && birdDto.getUserId() != currentUserId.longValue()) {
			throw new AuthenticationException("401", "Không có quyền cập nhạt chim.");
		}

		Optional<Bird> requestChangeBird = birdRepo.findById(birdDto.getId());
		
		if (requestChangeBird.isEmpty()) {
			throw new BirdNotFoundException("404", "Chim không tồn tại.");
		}

		if (!StringUtils.hasText(birdDto.getName())) {
			throw new CustomException("400", "Tên chim không được để trống.");
		}

		try {
			birdRepo.doUserUpdate(birdDto.getName(), birdDto.getDescription(), birdDto.getImgUrl(), currentUserId,
					new Timestamp(System.currentTimeMillis()), birdDto.getId());
			entityManager.clear();
			Bird birdUpdated = birdRepo.findById(birdDto.getId()).get();
			if (ObjectUtils.isEmpty(birdUpdated)) {
				throw new Exception();
			}
			return BirdResponseDto.build(birdUpdated);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
