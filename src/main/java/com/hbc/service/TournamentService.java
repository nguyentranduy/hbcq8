package com.hbc.service;

import java.util.List;

import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;

public interface TournamentService {

	List<TourResponseDto> findAllAvailable();
	TourResponseDto findByIdAvailable(long tourId) throws Exception;
	TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser);
}
