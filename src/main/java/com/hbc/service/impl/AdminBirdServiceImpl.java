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

import com.hbc.constant.TourApplyStatusCodeConst;
import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.dto.bird.admin.AdminBirdRequestDto;
import com.hbc.dto.bird.admin.AdminBirdUpdateRequestDto;
import com.hbc.entity.Bird;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.BirdAlreadyRequestedException;
import com.hbc.exception.bird.BirdNotFoundException;
import com.hbc.exception.bird.DuplicatedBirdInfoException;
import com.hbc.repo.BirdRepo;
import com.hbc.repo.TournamentApplyRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminBirdService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AdminBirdServiceImpl implements AdminBirdService {
	
	@Autowired
	BirdRepo birdRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	TournamentApplyRepo tourApplyRepo;
	
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
	public BirdResponseDto register(AdminBirdRequestDto birdDto, Long currentUserId) throws Exception {
		if (!StringUtils.hasText(birdDto.getCode())) {
			throw new CustomException("400", "Mã kiềng không được để trống.");
		}
		
		if (birdRepo.existsByCode(birdDto.getCode())) {
			throw new DuplicatedBirdInfoException("400", "Mã kiềng đã tồn tại.");
		}

		try {
			birdRepo.doRegister(birdDto.getCode(), birdDto.getUserId(), currentUserId);
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
	public BirdResponseDto update(AdminBirdUpdateRequestDto birdDto, Long currentUserId) throws Exception {
		Optional<Bird> requestChangeBird = birdRepo.findById(birdDto.getId());
		
		if (requestChangeBird.isEmpty()) {
			throw new BirdNotFoundException("404", "Chim không tồn tại.");
		}
		
		if (!requestChangeBird.get().getCode().equalsIgnoreCase(birdDto.getCode())
				&& birdRepo.existsByCodeAndIsDeleted(birdDto.getCode(), false)) {
			throw new DuplicatedBirdInfoException("400", "Mã kiềng đã tồn tại.");
		}

		if (!StringUtils.hasText(birdDto.getName())) {
			throw new CustomException("400", "Tên chim không được để trống.");
		}

		try {
			birdRepo.doUpdate(birdDto.getCode(), birdDto.getDescription(), birdDto.getImgUrl(), currentUserId,
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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String code, long currentUserId) throws Exception {
		if (!birdRepo.existsByCode(code) || birdRepo.existsByCodeAndIsDeleted(code, true)) {
			throw new BirdNotFoundException("404", "Chim không tồn tại.");
		}

		if (tourApplyRepo.existsByBirdCodeAndRequesterIdAndStatusCodeNot(code, currentUserId,
				TourApplyStatusCodeConst.STATUS_CODE_REJECTED)) {
			throw new BirdAlreadyRequestedException("400", "Không thể xóa chim đang dự giải đua.");
		}

		try {
			birdRepo.deleteByCode(currentUserId, new Timestamp(System.currentTimeMillis()), code);
		} catch (Exception ex) {
			throw ex;
		}
	}
}
