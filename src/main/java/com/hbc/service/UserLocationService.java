package com.hbc.service;

import java.util.List;

import com.hbc.dto.userlocation.UserLocationRequestDto;
import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.exception.userlocation.DuplicatedLocationCodeException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.exception.userlocation.LocationNotFoundException;

public interface UserLocationService {

	List<UserLocationResponseDto> findByUserId(long userId);
	UserLocationResponseDto findById(long id, long userId) throws LocationNotFoundException;
	void doRegister(UserLocationRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, InvalidCodeException;
	void doUpdate(long locationId, UserLocationRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, LocationNotFoundException, InvalidCodeException;
	void doDelete(long locationId, long currentUserId);
}
