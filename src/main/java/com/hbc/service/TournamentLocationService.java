package com.hbc.service;

import com.hbc.dto.PagedResponse;
import com.hbc.dto.UserResponseDto;
import com.hbc.dto.tourlocation.TourLocationDto;

public interface TournamentLocationService {

	TourLocationDto findByTourId(long tourId) throws Exception;
	PagedResponse<TourLocationDto> findAll(int page, int size);
	void save(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception;
	void update(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception;
	void delete(long id) throws Exception;
}
