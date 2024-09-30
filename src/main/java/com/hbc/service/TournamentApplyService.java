package com.hbc.service;

import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;

import jakarta.servlet.http.HttpSession;

public interface TournamentApplyService {

	TourApplyResponseDto doRegister(TourApplyRequestDto request, HttpSession session)
			throws AuthenticationException, TourApplyException, CustomException;
}
