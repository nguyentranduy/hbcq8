package com.hbc.service;

import java.util.List;

import com.hbc.dto.userlocation.UserLocationAdminRequestDto;
import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.exception.userlocation.DuplicatedLocationCodeException;
import com.hbc.exception.userlocation.InvalidCodeException;
import com.hbc.exception.userlocation.LocationNotFoundException;

public interface AdminUserLocationService {

	List<UserLocationResponseDto> findByUserId(long userId);
	UserLocationResponseDto findByCode(String code) throws LocationNotFoundException;
	void doRegister(UserLocationAdminRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, InvalidCodeException, UserNotFoundException;
	void doUpdate(long userLocationId, UserLocationAdminRequestDto requestDto, long currentUserId)
			throws DuplicatedLocationCodeException, LocationNotFoundException, InvalidCodeException, UserNotFoundException;
	void doDelete(String code, long currentUserId) throws LocationNotFoundException;
}
