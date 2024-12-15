package com.hbc.service;

import java.util.List;

import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.exception.tournament.TourNotFoundException;

public interface TournamentService {

	List<TourResponseDto> findAllAvailable();
	TourResponseDto findById(long tourId) throws TourNotFoundException;
	TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser);
	TourResponseDto doUpdate(long tourId, TourRequestDto dto, UserResponseDto currentUser);
	void doFinished(long tourId, long approverId);
	void doDelete(long id, UserResponseDto currentUser) throws Exception;
}
