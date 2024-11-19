package com.hbc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.constant.TourApplyStatusCodeConst;
import com.hbc.dto.tournament.TournamentInfoDto;
import com.hbc.entity.Tournament;
import com.hbc.repo.TournamentApplyRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentInfoService;

@Service
public class TournamentInfoServiceImpl implements TournamentInfoService {

	@Autowired
	TournamentRepo tourRepo;

	@Autowired
	TournamentApplyRepo tourApplyRepo;

	@Override
	public List<TournamentInfoDto> doGetList(long requesterId) {
		List<Tournament> rawData = tourRepo.findByIsDeletedOrderByCreatedAtDesc(false);
		List<TournamentInfoDto> result = new ArrayList<>();

		rawData.forEach(item -> {
			TournamentInfoDto dto = new TournamentInfoDto();
			long tourId = item.getId();
			String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
			String memo = tourApplyRepo.findMemoByTourIdAndRequesterId(tourId, requesterId);
			
			dto.setTourId(tourId);
			dto.setTourName(item.getName());
			dto.setStartDateInfo(item.getStartDateInfo());
			dto.setEndDateInfo(item.getEndDateInfo());
			dto.setBirdsNum(item.getBirdsNum());
			dto.setTourApplyStatusCode(tourStatusCode);
			dto.setMemo(memo);
			result.add(dto);
		});

		return result;
	}

	@Override
	public List<TournamentInfoDto> doGetListMe(long requesterId) {
		List<Long> tourIds = tourApplyRepo.findByRequesterIdAndStatusCode(requesterId,
				TourApplyStatusCodeConst.STATUS_CODE_APPROVED);
		List<Tournament> rawData = tourRepo.findByIdInAndIsDeletedOrderByCreatedAtDesc(tourIds, false);
		
		List<TournamentInfoDto> result = new ArrayList<>();

		rawData.forEach(item -> {
			TournamentInfoDto dto = new TournamentInfoDto();
			long tourId = item.getId();
			String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
			String memo = tourApplyRepo.findMemoByTourIdAndRequesterId(tourId, requesterId);
			
			dto.setTourId(tourId);
			dto.setTourName(item.getName());
			dto.setStartDateInfo(item.getStartDateInfo());
			dto.setEndDateInfo(item.getEndDateInfo());
			dto.setBirdsNum(item.getBirdsNum());
			dto.setTourApplyStatusCode(tourStatusCode);
			dto.setMemo(memo);
			result.add(dto);
		});
		
		return result;
	}
}
