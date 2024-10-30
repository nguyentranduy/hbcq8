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
import com.hbc.dto.tournament.UpdateTourRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentLocation;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.exception.tournament.TourNotFoundException;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentService;
import com.hbc.validator.DateValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
	
	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	TournamentLocationRepo tourLocationRepo;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<TourResponseDto> findAllAvailable() {
		List<Tournament> tournaments = tourRepo.findByIsActivedOrderByCreatedAtDesc(true);
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
	public TourResponseDto findById(long tourId) throws Exception {
		Optional<Tournament> tournament = tourRepo.findById(tourId);
		if (tournament.isEmpty()) {
			throw new Exception();
		}
		
		Optional<TournamentLocation> tournamentLocation = tourLocationRepo.findByTourId(tourId);
		if (tournamentLocation.isEmpty()) {
			throw new Exception();
		}
		
		return TourResponseDto.build(tournament.get(), tournamentLocation.get());
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
		
		Tournament tourEntity = Tournament.build(dto, currentUser.getId());
		
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
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doUpdate(UpdateTourRequestDto dto, UserResponseDto currentUser) {
		log.info("User {} started update the tournament", currentUser.getUsername());
		
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}

		validateDto(dto);

		if (tourRepo.existsByNameAndIdNot(dto.getName(), dto.getId())) {
			throw new TourInfoFailedException("409-11", "Tên giải đua đã tồn tại.");
		}
		
		Tournament tourEntity = Tournament.buildForUpdate(dto, currentUser.getId());

		validateTourLocation(dto.getTourLocation());
		
		try {
			int tourUpdatedCount = tourRepo.update(tourEntity.getName(), tourEntity.getBirdsNum(), tourEntity.getStartDate(),
					tourEntity.getEndDate(), tourEntity.getRestTimePerDay(), tourEntity.getIsActived(),
					tourEntity.getUpdatedAt(), tourEntity.getUpdatedBy(), tourEntity.getId());
			if (tourUpdatedCount < 1) {
				throw new IllegalStateException();
			}
			
			TournamentLocation tourLocationEntity = convertToTourLocationEntity(dto.getTourLocation(), tourEntity, currentUser);
			tourLocationEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			tourLocationEntity.setUpdatedBy(currentUser.getId());
			
			tourLocationRepo.update(tourLocationEntity.getStartPointCode(), tourLocationEntity.getStartPointCoor(),
					tourLocationEntity.getPoint1Code(), tourLocationEntity.getPoint1Coor(), tourLocationEntity.getPoint1Dist(),
					tourLocationEntity.getPoint2Code(), tourLocationEntity.getPoint2Coor(), tourLocationEntity.getPoint2Dist(),
					tourLocationEntity.getPoint3Code(), tourLocationEntity.getPoint3Coor(), tourLocationEntity.getPoint3Dist(),
					tourLocationEntity.getPoint4Code(), tourLocationEntity.getPoint4Coor(), tourLocationEntity.getPoint4Dist(),
					tourLocationEntity.getPoint5Code(), tourLocationEntity.getPoint5Coor(), tourLocationEntity.getPoint5Dist(),
					tourLocationEntity.getEndPointCode(), tourLocationEntity.getEndPointCoor(), tourLocationEntity.getEndPointDist(),
					tourLocationEntity.getUpdatedAt(), tourLocationEntity.getUpdatedBy(), tourLocationEntity.getTourIdValue());
			
			entityManager.clear();
			
			Tournament tourEntityUpdated = tourRepo.findById(tourEntity.getId()).get();
			TournamentLocation tourLocationEntityUpdated = tourLocationRepo.findByTourId(tourEntity.getId()).get();

			return TourResponseDto.build(tourEntityUpdated, tourLocationEntityUpdated);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doDelete(long id, UserResponseDto currentUser) throws Exception {
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}
		
		if (!tourRepo.existsByIdAndIsActived(id, true)) {
			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
		}
		
		try {
			int countDeleted = tourRepo.deletePhysical(false, id);
			if (countDeleted < 1) {
				throw new Exception();
			}
		} catch (Exception ex) {
			throw ex;
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
		if (tourLocationDto.getStartPoint() == null || tourLocationDto.getStartPoint().getCode().isEmpty()) {
			throw new TourInfoFailedException("409-12", "Điểm bắt đầu không được bỏ trống.");
		}
		
		if (tourLocationDto.getEndPoint() == null || tourLocationDto.getEndPoint().getCode().isEmpty()) {
			throw new TourInfoFailedException("409-13", "Điểm kết thúc không được bỏ trống");
		}
		
		return;
	}
	
	private TournamentLocation convertToTourLocationEntity(TourLocationDto tourLocationDto, Tournament tour, UserResponseDto currentUser) {
		TournamentLocation entity = new TournamentLocation();
		
		entity.setStartPointCode(tourLocationDto.getStartPoint().getCode());
		entity.setStartPointCoor(tourLocationDto.getStartPoint().getCoor());
		
		entity.setEndPointCode(tourLocationDto.getEndPoint().getCode());
		entity.setEndPointCoor(tourLocationDto.getEndPoint().getCoor());
		entity.setEndPointDist(tourLocationDto.getEndPoint().getDist());
		
		entity.setTour(tour);
		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		entity.setCreatedBy(currentUser.getId());
		
		boolean existsPoint1 = false;
		boolean existsPoint2 = false;
		boolean existsPoint3 = false;
		boolean existsPoint4 = false;
		
		if (tourLocationDto.getPoint1() != null) {
			entity.setPoint1Code(tourLocationDto.getPoint1().getCode());
			entity.setPoint1Coor(tourLocationDto.getPoint1().getCoor());
			entity.setPoint1Dist(tourLocationDto.getPoint1().getDist());
			existsPoint1 = true;
		} else {
			entity.setPoint1Code(null);
			entity.setPoint1Coor(null);
			entity.setPoint1Dist(0F);
		}
		
		if (existsPoint1 && tourLocationDto.getPoint2() != null) {
			entity.setPoint2Code(tourLocationDto.getPoint2().getCode());
			entity.setPoint2Coor(tourLocationDto.getPoint2().getCoor());
			entity.setPoint2Dist(tourLocationDto.getPoint2().getDist());
			existsPoint2 = true;
		} else {
			entity.setPoint2Code(null);
			entity.setPoint2Coor(null);
			entity.setPoint2Dist(0F);
		}
		
		if (existsPoint2 && tourLocationDto.getPoint3() != null) {
			entity.setPoint3Code(tourLocationDto.getPoint3().getCode());
			entity.setPoint3Coor(tourLocationDto.getPoint3().getCoor());
			entity.setPoint3Dist(tourLocationDto.getPoint3().getDist());
			existsPoint3 = true;
		} else {
			entity.setPoint3Code(null);
			entity.setPoint3Coor(null);
			entity.setPoint3Dist(0F);
		}
		
		if (existsPoint3 & tourLocationDto.getPoint4() != null) {
			entity.setPoint4Code(tourLocationDto.getPoint4().getCode());
			entity.setPoint4Coor(tourLocationDto.getPoint4().getCoor());
			entity.setPoint4Dist(tourLocationDto.getPoint4().getDist());
			existsPoint4 = true;
		} else {
			entity.setPoint4Code(null);
			entity.setPoint4Coor(null);
			entity.setPoint4Dist(0F);
		}
		
		if (existsPoint4 & tourLocationDto.getPoint5() != null) {
			entity.setPoint5Code(tourLocationDto.getPoint5().getCode());
			entity.setPoint5Coor(tourLocationDto.getPoint5().getCoor());
			entity.setPoint5Dist(tourLocationDto.getPoint5().getDist());
		} else {
			entity.setPoint5Code(null);
			entity.setPoint5Coor(null);
			entity.setPoint5Dist(0F);
		}
		
		return entity;
	}
}
