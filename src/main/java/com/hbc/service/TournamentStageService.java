package com.hbc.service;

import java.util.List;

import com.hbc.entity.TournamentStage;

public interface TournamentStageService {
	
	void updateStatus(long id, boolean isActive, long currentUserId);
	void doFinished(long id, long currentUserId);
	boolean isFinished(long id);
	List<TournamentStage> getStageIdsByTour(long tourId);
}
