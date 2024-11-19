package com.hbc.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.hbc.dto.pdf.PdfInputDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto;
import com.hbc.dto.tourdetail.TourDetailResponseDto.TourStageDetail;
import com.hbc.dto.tournament.AdminTourApproveDto;
import com.hbc.dto.tournament.AdminTourRejectDto;
import com.hbc.dto.tournament.TourSubmitTimeRequestDto;
import com.hbc.dto.tournament.ViewRankDto;
import com.hbc.entity.Bird;
import com.hbc.entity.TournamentDetail;
import com.hbc.entity.TournamentStage;
import com.hbc.entity.UserLocation;
import com.hbc.exception.tournament.submit.InvalidSubmitPointKeyException;
import com.hbc.exception.tournament.submit.OutOfTimeException;
import com.hbc.exception.tournament.submit.SubmitInfoNotFoundException;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.repo.TournamentStageRepo;
import com.hbc.repo.UserLocationRepo;
import com.hbc.service.TournamentDetailService;

@Service
public class TournamentDetailServiceImpl implements TournamentDetailService {

	@Autowired
	TournamentDetailRepo tourDetailRepo;

	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	TournamentStageRepo tourStageRepo;
	
	@Autowired
	UserLocationRepo userLocationRepo;

	@Override
	public TourDetailResponseDto findByTourIdAndUserId(long tourId, long userId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndUser_Id(tourId, userId);
		List<TournamentStage> tourStageRaw = tourStageRepo.findByTourId(tourId);
		
		List<TourStageDetail> tourStages = new ArrayList<>();
		tourStageRaw.forEach(i -> {
			TourStageDetail detail = new TourStageDetail(i.getId(), i.getOrderNo(), i.getDescription(), i.getIsActived());
			tourStages.add(detail);
		});
		
		List<String> birdCodes = tourDetails.stream().map(TournamentDetail::getBird).map(Bird::getCode).distinct().toList();
		
		return new TourDetailResponseDto(tourId, tourStages, birdCodes);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public PdfInputDto doSubmitTime(TourSubmitTimeRequestDto requestDto, long userId) throws Exception {
		validatePointKey(requestDto.getPointKey());
		
		Timestamp submitTimeNow = new Timestamp(System.currentTimeMillis());

		TournamentDetail tourDetail = tourDetailRepo.findByTourIdAndUserIdAndBirdCodeAndTourStage_Id(requestDto.getTourId(),
				requestDto.getRequesterId(), requestDto.getBirdCode(), requestDto.getStageId());
		
		if (ObjectUtils.isEmpty(tourDetail)) {
			throw new SubmitInfoNotFoundException("404", "Thông tin chặng đua không tồn tại.");
		}
		
		if (!ObjectUtils.isEmpty(tourDetail.getEndPointSubmitTime())
				&& !checkSubmitTime(tourDetail.getEndPointSubmitTime(), submitTimeNow)) {
			throw new OutOfTimeException("408", "Quá thời hạn chỉnh sửa lại.");
		}
		
		Optional<TournamentStage> tourStage = tourStageRepo.findById(requestDto.getStageId());
		
		if (tourStage.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (!tourStage.get().getIsActived()) {
			throw new OutOfTimeException("408", "Chặng đua không cho phép báo cáo.");
		}
		
		double time = calculateTimeDifferenceInHours(tourStage.get().getStartTime(), submitTimeNow, tourStage.get().getRestTimePerDay());
		double speed = tourDetail.getEndPointDist()/time;
		
		tourDetailRepo.doUpdateEndPoint(requestDto.getPointKey(), submitTimeNow, submitTimeNow, speed,
				requestDto.getTourId(), userId, requestDto.getBirdCode(), requestDto.getStageId());


		return new PdfInputDto(requestDto.getTourId(), tourDetail.getEndPointCode(),
				requestDto.getBirdCode(), requestDto.getPointKey(), timestampToString(submitTimeNow));
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
//		List<TournamentDetail> tourDetails = tourDetailRepo.findByTour_IdAndStatus(tourId, TourDetailStatusCodeConst.STATUS_CODE_WAITING);
//		return tourDetails.stream().map(TourDetailResponseDto::build).toList();
		return null;
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
	public List<ViewRankDto> viewRankByTourIdAndStageId(long tourId, long stageId) {
		List<TournamentDetail> tourDetails = tourDetailRepo.findByTourStage_IdAndTour_IdAndStatusNotNullOrderByEndPointSpeedDesc(stageId, tourId);
		List<String> userLocationCodes = tourDetails.stream().map(TournamentDetail::getEndPointCode).toList();
		List<UserLocation> userLocations = userLocationRepo.findByCodeIn(userLocationCodes);
		Map<String, String> userLocationMap = userLocations.stream()
				.collect(Collectors.toMap(UserLocation::getCode, UserLocation::getName));
		int i = 1;
		
		List<ViewRankDto> result = new ArrayList<>();
		for (TournamentDetail item : tourDetails) {
			String userLocationName = userLocationMap.get(item.getEndPointCode());
			double totalTime = calculateTimeDifferenceInHours(item.getTourStage().getStartTime(), item.getEndPointTime(),
					item.getTourStage().getRestTimePerDay());
			ViewRankDto dto = new ViewRankDto(i++, item.getEndPointCode(), userLocationName,
					item.getBird().getCode(), item.getEndPointCoor(), item.getEndPointDist(),
					item.getEndPointTime(), item.getTourStage().getStartTime(),
					doubleToHHMMSS(totalTime), item.getEndPointSpeed());
			result.add(dto);
		}
		return result;
	}
	
	private String doubleToHHMMSS(double time) {
	    long totalSeconds = (long) (time * 3600);

	    int hours = (int) (totalSeconds / 3600);
	    int minutes = (int) ((totalSeconds % 3600) / 60);
	    int seconds = (int) (totalSeconds % 60);

	    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
