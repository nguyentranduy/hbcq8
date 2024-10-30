package com.hbc.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.dto.PagedResponse;
import com.hbc.dto.Pagination;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentLocation;
import com.hbc.exception.tourlocation.CreateTourLocationException;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentLocationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TournamentLocationServiceImpl implements TournamentLocationService {
	
	@Autowired
	TournamentLocationRepo repo;
	
	@Autowired
	TournamentRepo tournamentRepo;

	@Override
	public TourLocationDto findByTourId(long tourId) throws Exception {
		Optional<TournamentLocation> tournamentLocation = repo.findByTourId(tourId);
		
		if (tournamentLocation.isEmpty()) {
			throw new Exception();
		}
		
		return TourLocationDto.build(tournamentLocation.get());
	}

	@Override
	public PagedResponse<TourLocationDto> findAll(int page, int size) {
		Page<TournamentLocation> tourLocationList = repo.findAll(PageRequest.of(page, size));

		Pagination pagination = new Pagination(tourLocationList.getNumber(), tourLocationList.getTotalPages(),
				tourLocationList.getTotalElements(), tourLocationList.getSize());

		List<TourLocationDto> pagedList = tourLocationList.stream().map(TourLocationDto::build)
				.collect(Collectors.toList());

		return new PagedResponse<TourLocationDto>(pagedList, pagination);
	}
	
	@Override
	@Transactional
	public void update(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception {
		log.info("User {} started update tour location with id - {}", currentUser.getUsername(), tourLocationDto.getId());
		if (!repo.existsById(tourLocationDto.getId())) {
			throw new CreateTourLocationException("400", "Location is not exists.");
		}
		
		TournamentLocation beforeUpdatedLocation = repo.findByIdAndTourId(tourLocationDto.getId(),
				tourLocationDto.getTourId());
		
		if (beforeUpdatedLocation == null) {
			throw new CreateTourLocationException("400", "Cannot perform update tourId.");
		}
		
		Tournament tour = tournamentRepo.findByIdAndIsActived(tourLocationDto.getTourId(), true);
		if (tour == null) {
			throw new CreateTourLocationException("400", "Tour is not exists.");
		}
	
		validateDto(tourLocationDto);
		
		TournamentLocation targetEntity = convertToEntity(tourLocationDto, tour);
		targetEntity.setCreatedBy(beforeUpdatedLocation.getCreatedBy());
		targetEntity.setCreatedAt(beforeUpdatedLocation.getCreatedAt());
		targetEntity.setUpdatedBy(currentUser.getId());
		targetEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
		
		try {
			repo.saveAndFlush(targetEntity);
		} catch (Exception ex) {
			log.warn("Cannot update tour location with id - {}", targetEntity.getId());
			throw ex;
		}
	}
	
	@Override
	@Transactional
	public void delete(long id) throws Exception {
		if (!repo.existsById(id)) {
			throw new CreateTourLocationException("400", "Location is not exists.");
		}
		
		try {
			repo.deleteById(id);
		} catch (Exception ex) {
			log.warn("Cannot delete tour location with id - {}", id);
			throw ex;
		}
	}
	
	private void validateDto(TourLocationDto tourLocationDto) {
		if (tourLocationDto.getStartPoint() == null || tourLocationDto.getStartPoint().getCode().isEmpty()) {
			throw new CreateTourLocationException("400", "Start point name must be not empty.");
		}
		
		if (tourLocationDto.getEndPoint() == null || tourLocationDto.getEndPoint().getCode().isEmpty()) {
			throw new CreateTourLocationException("400", "End point name must be not empty.");
		}
		
		return;
	}
	
	private TournamentLocation convertToEntity(TourLocationDto tourLocationDto, Tournament tour) {
		TournamentLocation entity = new TournamentLocation();
		
		if (tourLocationDto.getId() != null) {
			entity.setId(tourLocationDto.getId());
		}
		
		entity.setStartPointCode(tourLocationDto.getStartPoint().getCode());
		entity.setStartPointCoor(tourLocationDto.getStartPoint().getCoor());
		
		entity.setEndPointCode(tourLocationDto.getEndPoint().getCode());
		entity.setEndPointCoor(tourLocationDto.getEndPoint().getCoor());
		entity.setEndPointDist(tourLocationDto.getEndPoint().getDist());
		
		entity.setTour(tour);
		
		if (tourLocationDto.getPoint1() != null) {
			entity.setPoint1Code(tourLocationDto.getPoint1().getCode());
			entity.setPoint1Coor(tourLocationDto.getPoint1().getCoor());
			entity.setPoint1Dist(tourLocationDto.getPoint1().getDist());
		}
		
		if (tourLocationDto.getPoint2() != null) {
			entity.setPoint2Code(tourLocationDto.getPoint2().getCode());
			entity.setPoint2Coor(tourLocationDto.getPoint2().getCoor());
			entity.setPoint2Dist(tourLocationDto.getPoint2().getDist());
		}
		
		if (tourLocationDto.getPoint3() != null) {
			entity.setPoint3Code(tourLocationDto.getPoint3().getCode());
			entity.setPoint3Coor(tourLocationDto.getPoint3().getCoor());
			entity.setPoint3Dist(tourLocationDto.getPoint3().getDist());
		}
		
		if (tourLocationDto.getPoint4() != null) {
			entity.setPoint4Code(tourLocationDto.getPoint4().getCode());
			entity.setPoint4Coor(tourLocationDto.getPoint4().getCoor());
			entity.setPoint4Dist(tourLocationDto.getPoint4().getDist());
		}
		
		if (tourLocationDto.getPoint5() != null) {
			entity.setPoint5Code(tourLocationDto.getPoint5().getCode());
			entity.setPoint5Coor(tourLocationDto.getPoint5().getCoor());
			entity.setPoint5Dist(tourLocationDto.getPoint5().getDist());
		}
		
		return entity;
	}
}
