package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.dto.systemlocation.SystemLocationRequestDto;
import com.hbc.dto.systemlocation.SystemLocationResponseDto;
import com.hbc.entity.SystemLocation;
import com.hbc.exception.systemlocation.DuplicatedSystemLocationCodeException;
import com.hbc.exception.systemlocation.InvalidSystemLocationCodeException;
import com.hbc.exception.systemlocation.SystemLocationNotFoundException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.repo.SystemLocationRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminSystemLocationService;

@Service
public class AdminSystemLocationServiceImpl implements AdminSystemLocationService {

	@Autowired
	SystemLocationRepo repo;
	
	@Autowired
	UserRepo userRepo;
    
    private static final String CODE_PATTERN = "^P\\d{3,4}$";

	@Override
	public List<SystemLocationResponseDto> findAllAvailable() {
		List<SystemLocation> rawData = repo.findByIsDeleted(false);
		return rawData.stream().map(SystemLocationResponseDto::build).toList();
	}

	@Override
	public SystemLocationResponseDto findByCode(String code) throws SystemLocationNotFoundException {
		SystemLocation systemLocation = repo.findByCodeAndIsDeleted(code, false);

		if (Objects.isNull(systemLocation)) {
			throw new SystemLocationNotFoundException("404", "Không tìm thấy điểm thả.");
		}

		return SystemLocationResponseDto.build(systemLocation);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doRegister(SystemLocationRequestDto requestDto, long currentUserId)
			throws DuplicatedSystemLocationCodeException, InvalidSystemLocationCodeException {
		
		if (!requestDto.getCode().matches(CODE_PATTERN)) {
			throw new InvalidCodeException("400", "Mã điểm thả không đúng định dạng.");
		}

		if (repo.existsByCodeAndIsDeleted(requestDto.getCode(), false)) {
			throw new DuplicatedSystemLocationCodeException("409", "Mã điểm thả đã tồn tại.");
		}
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		repo.doRegister(requestDto.getCode(), requestDto.getName(), requestDto.getPointCoor(), createdAt, currentUserId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doUpdate(long systemLocationId, SystemLocationRequestDto requestDto, long currentUserId)
			throws SystemLocationNotFoundException, DuplicatedSystemLocationCodeException, InvalidSystemLocationCodeException {

		SystemLocation location = repo.findByIdAndIsDeleted(systemLocationId, false);
		if (Objects.isNull(location)) {
			throw new SystemLocationNotFoundException("404", "Không tìm thấy điểm thả.");
		}

		if (!requestDto.getCode().matches(CODE_PATTERN)) {
			throw new InvalidCodeException("400", "Mã điểm thả không đúng định dạng.");
		}

		if (!requestDto.getCode().equalsIgnoreCase(location.getCode())
				&& repo.existsByCodeAndIsDeleted(requestDto.getCode(), false)) {
			throw new DuplicatedSystemLocationCodeException("409", "Mã điểm thả đã tồn tại.");
		}

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		repo.doUpdate(requestDto.getCode(), requestDto.getName(), requestDto.getPointCoor(),
				updatedAt, currentUserId, systemLocationId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doDelete(long systemLocationId, long currentUserId) throws SystemLocationNotFoundException {
		SystemLocation location = repo.findByIdAndIsDeleted(systemLocationId, false);
		if (Objects.isNull(location)) {
			throw new SystemLocationNotFoundException("404", "Không tìm thấy điểm thả.");
		}

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		repo.doLogicalDelete(updatedAt, currentUserId, systemLocationId);
	}
}
