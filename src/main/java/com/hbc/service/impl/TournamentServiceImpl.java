package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.constant.RoleConst;
import com.hbc.dto.tournament.TourLocationDto;
import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentLocation;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentService;
import com.hbc.validator.DateValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
	
	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	TournamentLocationRepo tourLocationRepo;

	@Override
	public List<TourResponseDto> findAllAvailable() {
		List<Tournament> tournaments = tourRepo.findByIsActived(true);
		List<Long> tourIds = tournaments.stream().map(Tournament::getId).toList();
		List<TournamentLocation> tournamentLocations = tourLocationRepo.findListByTourIds(tourIds);
		
		Map<Long, TournamentLocation> tournamentLocationMap = tournamentLocations.stream()
				.collect(Collectors.toMap(TournamentLocation::getTourIdValue, tournamentLocation -> tournamentLocation));

		List<TourResponseDto> response = new ArrayList<>();
		tournaments.forEach(tour -> {
			response.add(TourResponseDto.build(tour, tournamentLocationMap.get(tour.getId())));
		});
		
		return response;
	}

	@Override
	public TourResponseDto findByIdAvailable(long tourId) throws Exception {
		Tournament tournament = tourRepo.findByIdAndIsActived(tourId, true);
		Optional<TournamentLocation> tournamentLocation = tourLocationRepo.findByTourId(tourId);
		
		if (tournamentLocation.isEmpty()) {
			throw new Exception();
		}
		
		return TourResponseDto.build(tournament, tournamentLocation.get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser) {
		log.info("User {} started create new tournament", currentUser.getUsername());
		
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}
		
		validateDto(dto);
		
		if (tourRepo.existsByName(dto.getName())) {
			throw new TourInfoFailedException("409-11", "Tên giải đua đã tồn tại.");
		}
		
		Tournament tourEntity = Tournament.build(dto, currentUser.getId(), 0);
		
		validateTourLocation(dto.getTourLocation());
		
		try {
			Tournament tourResponse = tourRepo.save(tourEntity);
			TournamentLocation tourLocationEntity = convertToTourLocationEntity(dto.getTourLocation(), tourResponse, currentUser);
			TournamentLocation tourLocationResponse = tourLocationRepo.save(tourLocationEntity);
			return TourResponseDto.build(tourResponse, tourLocationResponse);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}
	
	private void validateDto(TourRequestDto dto) {
		if (!StringUtils.hasText(dto.getName())) {
			throw new CustomException("400", "Tên giải đua không được bỏ trống.");
		}
		
		if (!DateValidator.isValidPeriod(dto.getStartDate(), dto.getEndDate())) {
			throw new CustomException("400", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
		}
		
		if ((ObjectUtils.isEmpty(dto.getStartDate()) || ObjectUtils.isEmpty(dto.getEndDate())) 
				&& dto.getIsActived()) {
			throw new CustomException("400", "Không thể bắt đầu giải đua khi chưa chọn ngày bắt đầu & ngày kết thúc.");
		}
	}
	
	private void validateTourLocation(TourLocationDto tourLocationDto) {
		if (tourLocationDto.getStartPoint() == null || tourLocationDto.getStartPoint().getName().isEmpty()) {
			throw new TourInfoFailedException("409-12", "Điểm bắt đầu không được bỏ trống.");
		}
		
		if (tourLocationDto.getEndPoint() == null || tourLocationDto.getEndPoint().getName().isEmpty()) {
			throw new TourInfoFailedException("409-13", "Điểm kết thúc không được bỏ trống");
		}
		
		return;
	}
	
	private TournamentLocation convertToTourLocationEntity(TourLocationDto tourLocationDto, Tournament tour, UserResponseDto currentUser) {
		TournamentLocation entity = new TournamentLocation();
		
		entity.setStartPointName(tourLocationDto.getStartPoint().getName());
		entity.setStartPointCoor(tourLocationDto.getStartPoint().getCoor());
		
		entity.setEndPointName(tourLocationDto.getEndPoint().getName());
		entity.setEndPointCoor(tourLocationDto.getEndPoint().getCoor());
		entity.setEndPointDist(tourLocationDto.getEndPoint().getDist());
		
		entity.setTour(tour);
		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		entity.setCreatedBy(currentUser.getId());
		
		if (tourLocationDto.getPoint1() != null) {
			entity.setPoint1Name(tourLocationDto.getPoint1().getName());
			entity.setPoint1Coor(tourLocationDto.getPoint1().getCoor());
			entity.setPoint1Dist(tourLocationDto.getPoint1().getDist());
		}
		
		if (tourLocationDto.getPoint2() != null) {
			entity.setPoint2Name(tourLocationDto.getPoint2().getName());
			entity.setPoint2Coor(tourLocationDto.getPoint2().getCoor());
			entity.setPoint2Dist(tourLocationDto.getPoint2().getDist());
		}
		
		if (tourLocationDto.getPoint3() != null) {
			entity.setPoint3Name(tourLocationDto.getPoint3().getName());
			entity.setPoint3Coor(tourLocationDto.getPoint3().getCoor());
			entity.setPoint3Dist(tourLocationDto.getPoint3().getDist());
		}
		
		if (tourLocationDto.getPoint4() != null) {
			entity.setPoint4Name(tourLocationDto.getPoint4().getName());
			entity.setPoint4Coor(tourLocationDto.getPoint4().getCoor());
			entity.setPoint4Dist(tourLocationDto.getPoint4().getDist());
		}
		
		if (tourLocationDto.getPoint5() != null) {
			entity.setPoint5Name(tourLocationDto.getPoint5().getName());
			entity.setPoint5Coor(tourLocationDto.getPoint5().getCoor());
			entity.setPoint5Dist(tourLocationDto.getPoint5().getDist());
		}
		
		return entity;
	}
}
