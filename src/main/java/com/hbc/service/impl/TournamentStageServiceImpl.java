package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.entity.TournamentStage;
import com.hbc.repo.TournamentStageRepo;
import com.hbc.service.TournamentStageService;

@Service
public class TournamentStageServiceImpl implements TournamentStageService {

	@Autowired
	TournamentStageRepo repo;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStatus(long id, boolean isActive, long currentUserId) {
		repo.updateStatus(isActive, new Timestamp(System.currentTimeMillis()), currentUserId, id);
	}

	@Override
	public List<TournamentStage> getStageIdsByTour(long tourId) {
		return repo.findByTourId(tourId);
	}
}
