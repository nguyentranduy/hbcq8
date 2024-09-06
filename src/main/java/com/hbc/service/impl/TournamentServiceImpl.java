package com.hbc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.entity.Tournament;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentService;

@Service
public class TournamentServiceImpl implements TournamentService {
	
	@Autowired
	TournamentRepo repo;

	@Override
	public List<Tournament> findAllAvailable() {
		return repo.findByIsActived(true);
	}

	@Override
	public Tournament findByIdAvailable(long tourId) {
		return repo.findByIdAndIsActived(tourId, true);
	}
}
