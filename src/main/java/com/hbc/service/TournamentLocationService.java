package com.hbc.service;

import com.hbc.dto.PagedResponse;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.dto.user.UserResponseDto;

public interface TournamentLocationService {

	TourLocationDto findByTourId(long tourId) throws Exception;
	PagedResponse<TourLocationDto> findAll(int page, int size);
	void save(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception;
	void update(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception;
	void delete(long id) throws Exception;
}
