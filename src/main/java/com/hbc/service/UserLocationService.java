package com.hbc.service;

import java.util.List;

import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.exception.userlocation.LocationNotFoundException;

public interface UserLocationService {

	List<UserLocationResponseDto> findByUserId(long userId);
	UserLocationResponseDto findById(long id, long userId) throws LocationNotFoundException;
}
