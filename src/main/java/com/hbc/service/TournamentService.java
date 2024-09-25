package com.hbc.service;

import java.util.List;

import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;

public interface TournamentService {

	List<TourResponseDto> findAllAvailable();
	Tournament findByIdAvailable(long tourId);
	TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser);
}
