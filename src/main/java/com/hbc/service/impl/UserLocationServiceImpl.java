package com.hbc.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.userlocation.UserLocationResponseDto;
import com.hbc.entity.UserLocation;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.repo.UserLocationRepo;
import com.hbc.service.UserLocationService;

@Service
public class UserLocationServiceImpl implements UserLocationService {

	@Autowired
	UserLocationRepo repo;
    
	@Override
	public List<UserLocationResponseDto> findByUserId(long userId) {
		List<UserLocation> userLocationList = repo.findByUser_IdAndIsDeletedOrderByCreatedAtAsc(userId, false);
		return userLocationList.stream().map(UserLocationResponseDto::build).toList();
	}

	@Override
	public UserLocationResponseDto findById(long id, long userId) throws LocationNotFoundException {
		UserLocation userLocation = repo.findByIdAndUser_IdAndIsDeleted(id, userId, false);

		if (Objects.isNull(userLocation)) {
			throw new LocationNotFoundException("404", "Không tìm thấy căn cứ.");
		}

		return UserLocationResponseDto.build(userLocation);
	}
}
