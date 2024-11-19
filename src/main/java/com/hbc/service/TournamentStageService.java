package com.hbc.service;

import java.util.List;

public interface TournamentStageService {
	
	void updateStatus(long id, boolean isActive, long currentUserId);
	List<Long> getStageIdsByTour(long tourId);
}
