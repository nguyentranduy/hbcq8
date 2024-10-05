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

import com.hbc.dto.bird.BirdRequestDto;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.BirdUpdateRequestDto;
import com.hbc.entity.Bird;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.BirdNotFoundException;
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
	public BirdResponseDto register(BirdRequestDto birdDto, Long currentUserId) throws Exception {
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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BirdResponseDto update(BirdUpdateRequestDto birdDto, Long currentUserId) throws Exception {
		Optional<Bird> requestChangeBird = birdRepo.findById(birdDto.getId());
		
		if (requestChangeBird.isEmpty()) {
			throw new BirdNotFoundException("404", "Chim không tồn tại.");
		}

		if (!StringUtils.hasText(birdDto.getCode())) {
			throw new CustomException("400", "Mã kiềng không được để trống.");
		}

		if (!StringUtils.hasText(birdDto.getName())) {
			throw new CustomException("400", "Tên chim không được để trống.");
		}

		if (birdRepo.existsByCodeAndIdNot(birdDto.getCode(), birdDto.getId())) {
			throw new DuplicatedBirdInfoException("400", "Mã kiềng đã tồn tại.");
		}

		try {
			birdRepo.doUpdate(birdDto.getName(), birdDto.getCode(), birdDto.getImgUrl(), currentUserId,
					new Timestamp(System.currentTimeMillis()), birdDto.getUserId());
			entityManager.clear();
			Bird birdUpdated = birdRepo.findByCode(birdDto.getCode());
			if (ObjectUtils.isEmpty(birdUpdated)) {
				throw new Exception();
			}
			return BirdResponseDto.build(birdUpdated);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void delete(String code, long currentUserId) throws Exception {
		if (!birdRepo.existsByCode(code)) {
			throw new BirdNotFoundException("404", "Chim không tồn tại.");
		}

		if (birdRepo.existsByCodeAndUserId(code, currentUserId)) {
			throw new AuthenticationException("401", "Không có quyền xóa.");
		}

		try {
			birdRepo.deleteByCode(code);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
