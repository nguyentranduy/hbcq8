package com.hbc.service;

public interface TournamentStageService {
	
	void updateStatus(long id, boolean isActive, long currentUserId);
}
