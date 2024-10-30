package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.dto.userlocation.UserLocationAdminRequestDto;
import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.entity.UserLocation;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.exception.userlocation.DuplicatedLocationCodeException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.repo.UserLocationRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminUserLocationService;

@Service
public class AdminUserLocationServiceImpl implements AdminUserLocationService {

	@Autowired
	UserLocationRepo repo;
	
	@Autowired
	UserRepo userRepo;
    
    private static final String CODE_PATTERN = "^Z\\d{3,4}$";

	@Override
	public List<UserLocationResponseDto> findByUserId(long userId) {
		List<UserLocation> userLocationList = repo.findByUser_IdAndIsDeletedOrderByCreatedAtAsc(userId, false);
		return userLocationList.stream().map(UserLocationResponseDto::build).toList();
	}

	@Override
	public UserLocationResponseDto findByCode(String code) throws LocationNotFoundException {
		UserLocation userLocation = repo.findByCodeAndIsDeleted(code, false);

		if (Objects.isNull(userLocation)) {
			throw new LocationNotFoundException("404", "Không tìm thấy căn cứ.");
		}

		return UserLocationResponseDto.build(userLocation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doRegister(UserLocationAdminRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, InvalidCodeException, UserNotFoundException {

		if (!userRepo.existsByIdAndIsDeleted(requestDto.getUserId(), false)) {
			throw new UserNotFoundException("404", "Người dùng không tồn tại.");
		}
		
		if (!requestDto.getCode().matches(CODE_PATTERN)) {
			throw new InvalidCodeException("400", "Mã căn cứ không đúng định dạng.");
		}

		if (repo.existsByCodeAndIsDeleted(requestDto.getCode(), false)) {
			throw new DuplicatedLocationCodeException("409", "Tên căn cứ đã tồn tại.");
		}
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		repo.doRegister(requestDto.getCode(), requestDto.getUserId(), requestDto.getPointCoor(), createdAt, currentUserId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doUpdate(String code, UserLocationAdminRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, LocationNotFoundException,
			UserNotFoundException, InvalidCodeException {

		UserLocation userLocation = repo.findByCodeAndIsDeleted(code, false);
		if (Objects.isNull(userLocation)) {
			throw new LocationNotFoundException("404", "Không tìm thấy căn cứ.");
		}

		if (!userRepo.existsByIdAndIsDeleted(requestDto.getUserId(), false)) {
			throw new UserNotFoundException("404", "Người dùng không tồn tại.");
		}

		if (!requestDto.getCode().matches(CODE_PATTERN)) {
			throw new InvalidCodeException("400", "Mã căn cứ không đúng định dạng.");
		}

		if (!requestDto.getCode().equalsIgnoreCase(userLocation.getCode())
				&& repo.existsByCodeAndIsDeleted(requestDto.getCode(), false)) {
			throw new DuplicatedLocationCodeException("409", "Tên căn cứ đã tồn tại.");
		}

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		repo.doUpdate(requestDto.getCode(), requestDto.getPointCoor(), updatedAt, currentUserId, requestDto.getUserId());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doDelete(String code, long currentUserId) throws LocationNotFoundException {
		UserLocation userLocation = repo.findByCodeAndIsDeleted(code, false);
		if (Objects.isNull(userLocation)) {
			throw new LocationNotFoundException("404", "Không tìm thấy căn cứ.");
		}

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		repo.doLogicalDeleteByCode(updatedAt, currentUserId, code);
	}
}
