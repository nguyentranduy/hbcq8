package com.hbc.service;

import java.util.List;

import com.hbc.dto.systemlocation.SystemLocationRequestDto;
import com.hbc.dto.systemlocation.SystemLocationResponseDto;
import com.hbc.exception.systemlocation.DuplicatedSystemLocationCodeException;
import com.hbc.exception.systemlocation.InvalidSystemLocationCodeException;
import com.hbc.exception.systemlocation.SystemLocationNotFoundException;

public interface AdminSystemLocationService {

	List<SystemLocationResponseDto> findAllAvailable();
	SystemLocationResponseDto findByCode(String code) throws SystemLocationNotFoundException;
	void doRegister(SystemLocationRequestDto requestDto, long currentUserId)
			throws DuplicatedSystemLocationCodeException, InvalidSystemLocationCodeException;
	void doUpdate(long systemLocationId, SystemLocationRequestDto requestDto, long currentUserId)
			throws SystemLocationNotFoundException, DuplicatedSystemLocationCodeException, InvalidSystemLocationCodeException;
	void doDelete(long systemLocationId, long currentUserId) throws SystemLocationNotFoundException;
}
