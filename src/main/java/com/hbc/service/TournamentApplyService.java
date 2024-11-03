package com.hbc.service;

import java.util.List;

import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyInfoDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyApproveDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;

import jakarta.servlet.http.HttpSession;

public interface TournamentApplyService {

	TourApplyResponseDto doRegister(TourApplyRequestDto request, HttpSession session)
			throws AuthenticationException, TourApplyException, CustomException;
	List<AdminTourApplyInfoDto> findByTourId(long tourId) throws Exception;
	void doApprove(AdminTourApplyApproveDto dto) throws Exception;
	void doCancel(long tourId, long requesterId) throws Exception;
}
