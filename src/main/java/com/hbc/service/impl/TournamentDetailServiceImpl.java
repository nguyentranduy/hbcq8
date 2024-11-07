package com.hbc.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.entity.TournamentDetail;
import com.hbc.exception.tournament.submit.InvalidSubmitInfoException;
import com.hbc.exception.tournament.submit.InvalidSubmitPointKeyException;
import com.hbc.exception.tournament.submit.OutOfTimeException;
import com.hbc.exception.tournament.submit.SubmitInfoNotFoundException;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.service.TournamentDetailService;

@Service
public class TournamentDetailServiceImpl implements TournamentDetailService {

	@Autowired
	TournamentDetailRepo tourDetailRepo;

	@Override
	public List<TourDetailResponseDto> findByTourIdAndUserId(long tourId, long userId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndUser_Id(tourId, userId);
		return tourDetails.stream().map(TourDetailResponseDto::build).toList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public PdfInputDto doSubmitTime(TourSubmitTimeRequestDto requestDto, long userId) throws Exception {
		Timestamp submitTimeNow = new Timestamp(System.currentTimeMillis());

		String pointKey = requestDto.getPointKey();

		validatePointKey(pointKey);
		
		TournamentDetail tourDetail = tourDetailRepo.findByTourIdAndUserIdAndBirdCode(requestDto.getTourId(),
				requestDto.getRequesterId(), requestDto.getBirdCode());
		
		if (ObjectUtils.isEmpty(tourDetail)) {
			throw new SubmitInfoNotFoundException("404", "Thông tin giải đua không tồn tại.");
		}
		
		long tourId = requestDto.getTourId();
		String birdCode = requestDto.getBirdCode();
		int pointNo = requestDto.getPointNo();
		String pointCode;
		if (pointNo == 1) {
			Timestamp point1SubmitTime = tourDetail.getPoint1SubmitTime();
			if (!ObjectUtils.isEmpty(point1SubmitTime) && !checkSubmitTime(point1SubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getPoint1Code();
			tourDetailRepo.doUpdatePoint1(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else if (pointNo == 2) {
			Timestamp point2SubmitTime = tourDetail.getPoint2SubmitTime();
			if (!ObjectUtils.isEmpty(point2SubmitTime) && !checkSubmitTime(point2SubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getPoint2Code();
			tourDetailRepo.doUpdatePoint2(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else if (pointNo == 3) {
			Timestamp point3SubmitTime = tourDetail.getPoint3SubmitTime();
			if (!ObjectUtils.isEmpty(point3SubmitTime) && !checkSubmitTime(point3SubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getPoint3Code();
			tourDetailRepo.doUpdatePoint3(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else if (pointNo == 4) {
			Timestamp point4SubmitTime = tourDetail.getPoint4SubmitTime();
			if (!ObjectUtils.isEmpty(point4SubmitTime) && !checkSubmitTime(point4SubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getPoint4Code();
			tourDetailRepo.doUpdatePoint4(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else if (pointNo == 5) {
			Timestamp point5SubmitTime = tourDetail.getPoint5SubmitTime();
			if (!ObjectUtils.isEmpty(point5SubmitTime) && !checkSubmitTime(point5SubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getPoint5Code();
			tourDetailRepo.doUpdatePoint5(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else if (pointNo == 0) {
			Timestamp endPointSubmitTime = tourDetail.getEndPointSubmitTime();
			if (!ObjectUtils.isEmpty(endPointSubmitTime) && !checkSubmitTime(endPointSubmitTime, submitTimeNow)) {
				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
			}
			pointCode = tourDetail.getEndPointCode();
			tourDetailRepo.doUpdateEndPoint(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
		} else {
			throw new InvalidSubmitInfoException("400", "Dữ liệu chưa đúng.");
		}

		return new PdfInputDto(tourId, pointCode, birdCode, pointKey, timestampToString(submitTimeNow));
	}

	private String timestampToString(Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(timestamp);
    }

	private boolean checkSubmitTime(Timestamp timeBefore, Timestamp timeAfter) {
		long diffInMillis = timeAfter.getTime() - timeBefore.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        return diffInMinutes <= 10;
	}

	private void validatePointKey(String pointKey) {
		String regex = "^\\d{5}$";

		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pointKey);

        if (!matcher.matches()) {
            throw new InvalidSubmitPointKeyException("400", "Mã bí mật không đúng định dạng.");
        }
	}
}
