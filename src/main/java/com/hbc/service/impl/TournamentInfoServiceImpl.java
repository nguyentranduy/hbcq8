package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.tournament.TournamentInfoDto;
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
		List<Object[]> rawData = tourRepo.getTournamentInfo();
		List<TournamentInfoDto> result = new ArrayList<>();
		
		rawData.forEach(item -> {
			TournamentInfoDto dto = new TournamentInfoDto();
			long tourId = (long) item[0];
			Timestamp startDate = (Timestamp) item[2];
			Timestamp endDate = (Timestamp) item[3];
			boolean isRawActived = (boolean) item[7];
			String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			dto.setTourId(tourId);
			dto.setTourName((String) item[1]);
			dto.setStartDate(startDate);
			dto.setEndDate(endDate);
			dto.setStartLocationName((String) item[4]);
			dto.setEndLocationName((String) item[5]);
			dto.setBirdsNum((int) item[6]);
			dto.setTourApplyStatusCode(tourStatusCode);
			dto.setActived(getIsActived(isRawActived, startDate, endDate, now));
			dto.setTourStatus(getStatus(isRawActived, startDate, endDate, now));
			result.add(dto);
		});
		
		return result;
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
