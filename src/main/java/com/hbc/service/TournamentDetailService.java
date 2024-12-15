package com.hbc.service;

import java.util.List;

import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tourdetail.ViewTourDetailDto;
import com.hbc.dto.tournament.AdminTourApproveDto;
import com.hbc.dto.tournament.AdminTourCancelDto;
import com.hbc.dto.tournament.AdminTourRejectDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.dto.tournament.ViewRankDto;

public interface TournamentDetailService {

	TourDetailResponseDto findByTourIdAndUserId(long tourId, long userId);
	PdfInputDto doSubmitTime(TourSubmitTimeRequestDto requestDto, long userId) throws Exception;
	List<ViewTourDetailDto> findByTourIdForApprove(long tourId, long stageId);
	void doApprove(AdminTourApproveDto dto, long approverId);
	void doReject(AdminTourRejectDto dto, long approverId);
	void doCancel(AdminTourCancelDto dto, long approverId);
	void doSortRankByTourId(long tourId);
	List<ViewRankDto> viewRankByTourIdAndStageId(long tourId, long stageId);
}
