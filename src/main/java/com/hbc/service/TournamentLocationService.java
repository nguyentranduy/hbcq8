package com.hbc.service;

import com.hbc.dto.tourlocation.TourLocationDto;

public interface TournamentLocationService {

	TourLocationDto findByTourId(long tourId);
}
