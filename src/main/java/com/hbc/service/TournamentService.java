package com.hbc.service;

import java.util.List;

import com.hbc.entity.Tournament;

public interface TournamentService {

	List<Tournament> findAllAvailable();
	Tournament findByIdAvailable(long tourId);
}
