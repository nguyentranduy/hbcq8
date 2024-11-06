package com.hbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.entity.TournamentDetail;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.service.TournamentDetailService;

@Service
public class TournamentDetailServiceImpl implements TournamentDetailService {

	@Autowired
	TournamentDetailRepo tourDetailRepo;

	@Override
	public List<TourDetailResponseDto> findByTourIdAndUserId(long tourId, long userId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndUser_Id(tourId, userId);
		return tourDetails.stream().map(TourDetailResponseDto::build).toList();
	}
}
