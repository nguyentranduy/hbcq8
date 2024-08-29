package com.hbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.entity.TournamentLocation;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.service.TournamentLocationService;

@Service
public class TournamentLocationServiceImpl implements TournamentLocationService {
	
	@Autowired
	TournamentLocationRepo repo;

	@Override
	public TourLocationDto findByTourId(long tourId) {
		TournamentLocation tournamentLocation = repo.findByTourId(tourId);
		return TourLocationDto.build(tournamentLocation);
	}
}
