package com.hbc.service;

import com.hbc.dto.PagedResponse;
import com.hbc.dto.tourlocation.TourLocationDto;

public interface TournamentLocationService {

	TourLocationDto findByTourId(long tourId) throws Exception;
	PagedResponse<TourLocationDto> findAll(int page, int size);
}
