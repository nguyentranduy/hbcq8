package com.hbc.service.impl;

import java.sql.Timestamp;
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
//		List<Tournament> rawData = tourRepo.findByIsDeletedAndIsActivedOrderByCreatedAtDesc(false, true);
//		List<TournamentInfoDto> result = new ArrayList<>();
//
//		rawData.forEach(item -> {
//			TournamentInfoDto dto = new TournamentInfoDto();
//			long tourId = item.getId();
//			String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
//			String memo = tourApplyRepo.findMemoByTourIdAndRequesterId(tourId, requesterId);
//			Timestamp now = new Timestamp(System.currentTimeMillis());
//			
//			dto.setTourId(tourId);
//			dto.setTourName(item.getName());
//			dto.setStartDate(item.getStartDate());
//			dto.setEndDate(item.getEndDate());
//			dto.setStartLocationCode(item.getStartPointCode());
//			dto.setEndLocationCode(item.getEndPointCode());
//			dto.setBirdsNum(item.getBirdsNum());
//			dto.setTourApplyStatusCode(tourStatusCode);
//			dto.setActived(getIsActived(item.getIsActived(), item.getStartDate(), item.getEndDate(), now));
//			dto.setTourStatus(getStatus(item.getIsActived(), item.getStartDate(), item.getEndDate(), now));
//			dto.setMemo(memo);
//			result.add(dto);
//		});
//		
//		return result;
		return null;
	}

	@Override
	public List<TournamentInfoDto> doGetListMe(long requesterId) {
//		List<Long> tourIds = tourApplyRepo.findByRequesterIdAndStatusCode(requesterId,
//				TourApplyStatusCodeConst.STATUS_CODE_APPROVED);
//		List<Tournament> rawData = tourRepo.findByIdInAndIsDeletedOrderByCreatedAtDesc(tourIds, false);
//		
//		List<TournamentInfoDto> result = new ArrayList<>();
//
//		rawData.forEach(item -> {
//			TournamentInfoDto dto = new TournamentInfoDto();
//			long tourId = item.getId();
//			String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
//			String memo = tourApplyRepo.findMemoByTourIdAndRequesterId(tourId, requesterId);
//			Timestamp now = new Timestamp(System.currentTimeMillis());
//			
//			dto.setTourId(tourId);
//			dto.setTourName(item.getName());
//			dto.setStartDate(item.getStartDate());
//			dto.setEndDate(item.getEndDate());
//			dto.setStartLocationCode(item.getStartPointCode());
//			dto.setEndLocationCode(item.getEndPointCode());
//			dto.setBirdsNum(item.getBirdsNum());
//			dto.setTourApplyStatusCode(tourStatusCode);
//			dto.setActived(getIsActived(item.getIsActived(), item.getStartDate(), item.getEndDate(), now));
//			dto.setTourStatus(getStatus(item.getIsActived(), item.getStartDate(), item.getEndDate(), now));
//			dto.setMemo(memo);
//			dto.setFinished(item.getIsFinished());
//			result.add(dto);
//		});
//		
//		return result;
		return null;
	}
	
	private boolean getIsActived(boolean isRawActived, Timestamp startDate, Timestamp endDate, Timestamp now) {
		if (!isRawActived) {
			return false;
		}
		
		if (now.before(startDate)) {
			return true;
		}
		
		return false;
	}
	
	private String getStatus(boolean isActived, Timestamp startDate, Timestamp endDate, Timestamp now) {
		if (now.before(startDate)) {
			return "Sắp diễn ra";
		}
		
		if (isActived
				&& (now.equals(startDate) || now.after(startDate))
				&& (now.equals(endDate) || now.before(endDate))) {
			return "Đang diễn ra";
		}
		
		return "Đã kết thúc";
	}
}
