package com.hbc.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.hbc.constant.TourApplyStatusCodeConst;
import com.hbc.constant.TourDetailStatusCodeConst;
import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tournament.AdminTourApproveDto;
import com.hbc.dto.tournament.AdminTourRejectDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.dto.tournament.ViewRankDto;
import com.hbc.entity.TournamentDetail;
import com.hbc.exception.tournament.submit.InvalidSubmitInfoException;
import com.hbc.exception.tournament.submit.InvalidSubmitPointKeyException;
import com.hbc.exception.tournament.submit.OutOfTimeException;
import com.hbc.exception.tournament.submit.SubmitInfoNotFoundException;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentDetailService;

@Service
public class TournamentDetailServiceImpl implements TournamentDetailService {

	@Autowired
	TournamentDetailRepo tourDetailRepo;

	@Autowired
	TournamentRepo tourRepo;

	@Override
	public List<TourDetailResponseDto> findByTourIdAndUserId(long tourId, long userId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndUser_Id(tourId, userId);
		return tourDetails.stream().map(TourDetailResponseDto::build).toList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public PdfInputDto doSubmitTime(TourSubmitTimeRequestDto requestDto, long userId) throws Exception {
		return null;
//		Timestamp submitTimeNow = new Timestamp(System.currentTimeMillis());
//
//		String pointKey = requestDto.getPointKey();
//
//		validatePointKey(pointKey);
//		
//		TournamentDetail tourDetail = tourDetailRepo.findByTourIdAndUserIdAndBirdCode(requestDto.getTourId(),
//				requestDto.getRequesterId(), requestDto.getBirdCode());
//		
//		if (ObjectUtils.isEmpty(tourDetail)) {
//			throw new SubmitInfoNotFoundException("404", "Thông tin giải đua không tồn tại.");
//		}
//		
//		long tourId = requestDto.getTourId();
//		String birdCode = requestDto.getBirdCode();
//		int pointNo = requestDto.getPointNo();
//		String pointCode;
//		if (pointNo == 1) {
//			Timestamp point1SubmitTime = tourDetail.getPoint1SubmitTime();
//			if (!ObjectUtils.isEmpty(point1SubmitTime) && !checkSubmitTime(point1SubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("408", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getPoint1Code();
//			tourDetailRepo.doUpdatePoint1(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else if (pointNo == 2) {
//			Timestamp point2SubmitTime = tourDetail.getPoint2SubmitTime();
//			if (!ObjectUtils.isEmpty(point2SubmitTime) && !checkSubmitTime(point2SubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getPoint2Code();
//			tourDetailRepo.doUpdatePoint2(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else if (pointNo == 3) {
//			Timestamp point3SubmitTime = tourDetail.getPoint3SubmitTime();
//			if (!ObjectUtils.isEmpty(point3SubmitTime) && !checkSubmitTime(point3SubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getPoint3Code();
//			tourDetailRepo.doUpdatePoint3(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else if (pointNo == 4) {
//			Timestamp point4SubmitTime = tourDetail.getPoint4SubmitTime();
//			if (!ObjectUtils.isEmpty(point4SubmitTime) && !checkSubmitTime(point4SubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getPoint4Code();
//			tourDetailRepo.doUpdatePoint4(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else if (pointNo == 5) {
//			Timestamp point5SubmitTime = tourDetail.getPoint5SubmitTime();
//			if (!ObjectUtils.isEmpty(point5SubmitTime) && !checkSubmitTime(point5SubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getPoint5Code();
//			tourDetailRepo.doUpdatePoint5(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else if (pointNo == 0) {
//			Timestamp endPointSubmitTime = tourDetail.getEndPointSubmitTime();
//			if (!ObjectUtils.isEmpty(endPointSubmitTime) && !checkSubmitTime(endPointSubmitTime, submitTimeNow)) {
//				throw new OutOfTimeException("400", "Quá thời hạn chỉnh sửa lại.");
//			}
//			pointCode = tourDetail.getEndPointCode();
//			tourDetailRepo.doUpdateEndPoint(pointKey, submitTimeNow, submitTimeNow, tourId, userId, birdCode);
//		} else {
//			throw new InvalidSubmitInfoException("400", "Dữ liệu chưa đúng.");
//		}
//
//		return new PdfInputDto(tourId, pointCode, birdCode, pointKey, timestampToString(submitTimeNow));
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

	@Override
	public List<TourDetailResponseDto> findByTourIdForApprove(long tourId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndStatus(tourId, TourDetailStatusCodeConst.STATUS_CODE_WAITING);
		return tourDetails.stream().map(TourDetailResponseDto::build).toList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doApprove(AdminTourApproveDto dto, long approverId) {
//		try {
//			TournamentDetail tourDetail = tourDetailRepo.findByTour_IdAndBird_Code(dto.getTourId(), dto.getBirdCode());
//			double point1Speed = 0.0;
//			double point2Speed = 0.0;
//			double point3Speed = 0.0;
//			double point4Speed = 0.0;
//			double point5Speed = 0.0;
//			double endPointSpeed = 0.0;
//			double avgSpeed = 0.0;
//			double restTimePerDay = Double.valueOf(tourRepo.findRestTimePerDayByTourId(dto.getTourId()));
//			if (!ObjectUtils.isEmpty(tourDetail.getPoint1Key())) {
//				double point1Dist = tourDetail.getPoint1Dist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getStartPointTime(), tourDetail.getPoint1Time(), restTimePerDay);
//				point1Speed = point1Dist/time;
//				avgSpeed = point1Speed;
//			}
//			
//			if (!ObjectUtils.isEmpty(tourDetail.getPoint2Key())) {
//				double point2Dist = tourDetail.getPoint2Dist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getPoint1Time(), tourDetail.getPoint2Time(), restTimePerDay);
//				point2Speed = point2Dist/time;
//				avgSpeed = (avgSpeed + point2Speed)/2;
//			}
//			
//			if (!ObjectUtils.isEmpty(tourDetail.getPoint3Key())) {
//				double point3Dist = tourDetail.getPoint3Dist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getPoint2Time(), tourDetail.getPoint3Time(), restTimePerDay);
//				point3Speed = point3Dist/time;
//				avgSpeed = (avgSpeed + point3Speed)/2;
//			}
//			
//			if (!ObjectUtils.isEmpty(tourDetail.getPoint4Key())) {
//				double point4Dist = tourDetail.getPoint4Dist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getPoint3Time(), tourDetail.getPoint4Time(), restTimePerDay);
//				point4Speed = point4Dist/time;
//				avgSpeed = (avgSpeed + point4Speed)/2;
//			}
//			
//			if (!ObjectUtils.isEmpty(tourDetail.getPoint5Key())) {
//				double point5Dist = tourDetail.getPoint5Dist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getPoint4Time(), tourDetail.getPoint5Time(), restTimePerDay);
//				point5Speed = point5Dist/time;
//				avgSpeed = (avgSpeed + point5Speed)/2;
//			}
//			
//			if (!ObjectUtils.isEmpty(tourDetail.getEndPointKey())) {
//				double endPointDist = tourDetail.getEndPointDist();
//				double time = calculateTimeDifferenceInHours(tourDetail.getPoint5Time(), tourDetail.getEndPointTime(), restTimePerDay);
//				endPointSpeed = endPointDist/time;
//				avgSpeed = (avgSpeed + endPointSpeed)/2;
//			}
//			
//			Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
//			tourDetailRepo.doApproveResult(point1Speed, point2Speed, point3Speed, point4Speed, point5Speed, endPointSpeed,
//					avgSpeed, updatedAt, approverId, dto.getTourId(), dto.getBirdCode());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	private double calculateTimeDifferenceInHours(Timestamp startTime, Timestamp endTime, double restTimePerDay) {
		double restTime = countNights(startTime, endTime) * restTimePerDay;
		System.out.println("resttimeeeeeeeeee: " + restTime);
        long diffInMillis = (long) (endTime.getTime() - startTime.getTime() - restTime*3600000);
        return (double) diffInMillis / (60 * 60 * 1000);
    }

	private double countNights(Timestamp startTime, Timestamp endTime) {
		LocalDateTime startDateTime = startTime.toLocalDateTime();
		LocalDateTime endDateTime = endTime.toLocalDateTime();

		if (startDateTime.isAfter(endDateTime)) {
			throw new IllegalArgumentException("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc");
		}

		long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);
		return endDateTime.toLocalDate().equals(startDateTime.toLocalDate())
				|| endDateTime.toLocalDate().equals(startDateTime.toLocalDate().plusDays(1)) ? daysBetween
						: daysBetween - 1;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doReject(AdminTourRejectDto dto, long approverId) {
		tourDetailRepo.doRejectResult(new Timestamp(System.currentTimeMillis()), approverId,
				dto.getTourId(), dto.getBirdCode());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doSortRankByTourId(long tourId) {
//		try {
//			List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndStatus(tourId,
//					TourApplyStatusCodeConst.STATUS_CODE_APPROVED);
//			LinkedHashMap<String, Float> result = tourDetails.stream()
//					.sorted(Comparator.comparingDouble(TournamentDetail::getAvgSpeed).reversed())
//					.collect(Collectors.toMap(tourDetail -> tourDetail.getBird().getCode(), TournamentDetail::getAvgSpeed,
//							(oldValue, newValue) -> oldValue, LinkedHashMap::new));
//			List<Map.Entry<String, Float>> entryList = new ArrayList<>(result.entrySet());
//	
//			for (int i = 0; i < entryList.size(); i++) {
//				String code = entryList.get(i).getKey();
//				tourDetailRepo.sortRankByTourId(i + 1, tourId, code);
//			}
//			tourRepo.doFinishedTour(false, true, tourId);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	@Override
	public List<ViewRankDto> viewRankByTourId(long tourId) {
		List<Object[]> rawData = tourDetailRepo.viewRankByTourId(tourId);
		List<ViewRankDto> result = new ArrayList<>();
		rawData.forEach(i -> {
			result.add(new ViewRankDto((long) i[0], (String) i[1], (float) i[2]));
		});
		return result;
	}
}
