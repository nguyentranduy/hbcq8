package com.hbc.service;

import java.util.List;

import com.hbc.dto.tourdetail.TourDetailResponseDto;

public interface TournamentDetailService {

	List<TourDetailResponseDto> findByTourIdAndUserId(long tourId, long userId);
}
