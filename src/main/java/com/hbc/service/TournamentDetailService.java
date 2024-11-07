package com.hbc.service;

import java.util.List;

import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tournament.AdminTourApproveDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.dto.tournament.ViewRankDto;

public interface TournamentDetailService {

	List<TourDetailResponseDto> findByTourIdAndUserId(long tourId, long userId);
	PdfInputDto doSubmitTime(TourSubmitTimeRequestDto requestDto, long userId) throws Exception;
	List<TourDetailResponseDto> findByTourIdForApprove(long tourId);
	void doApprove(AdminTourApproveDto dto, long approverId);
	void doSortRankByTourId(long tourId);
	List<ViewRankDto> viewRankByTourId(long tourId);
}
