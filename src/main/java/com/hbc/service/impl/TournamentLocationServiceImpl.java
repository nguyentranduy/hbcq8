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
import com.hbc.dto.UserResponseDto;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentLocation;
import com.hbc.exception.tourlocation.CreateTourLocationException;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.service.TournamentLocationService;
import com.hbc.service.TournamentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TournamentLocationServiceImpl implements TournamentLocationService {
	
	@Autowired
	TournamentLocationRepo repo;
	
	@Autowired
	TournamentService tournamentService;

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
	public void save(TourLocationDto tourLocationDto, UserResponseDto currentUser) throws Exception {
		log.info("User {} started create new tour location", currentUser.getUsername());
		Tournament tour = tournamentService.findByIdAvailable(tourLocationDto.getTourId());
		if (tour == null) {
			throw new CreateTourLocationException("400", "Tour is not exists.");
		}
		
		if (repo.existsByTourId(tourLocationDto.getTourId())) {
			throw new CreateTourLocationException("400", "TourId already exists.");
		}
	
		validateDto(tourLocationDto);
		
		TournamentLocation targetEntity = convertToEntity(tourLocationDto, tour);
		targetEntity.setCreatedBy(currentUser.getId());
		targetEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		
		try {
			repo.saveAndFlush(targetEntity);
		} catch (Exception ex) {
			log.warn("Cannot created new tour location with tourId - {}", targetEntity.getTour().getId());
			throw ex;
		}
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
		
		Tournament tour = tournamentService.findByIdAvailable(tourLocationDto.getTourId());
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
		if (tourLocationDto.getStartPoint() == null || tourLocationDto.getStartPoint().getName().isEmpty()) {
			throw new CreateTourLocationException("400", "Start point name must be not empty.");
		}
		
		if (tourLocationDto.getEndPoint() == null || tourLocationDto.getEndPoint().getName().isEmpty()) {
			throw new CreateTourLocationException("400", "End point name must be not empty.");
		}
		
		return;
	}
	
	private TournamentLocation convertToEntity(TourLocationDto tourLocationDto, Tournament tour) {
		TournamentLocation entity = new TournamentLocation();
		
		if (tourLocationDto.getId() != null) {
			entity.setId(tourLocationDto.getId());
		}
		
		entity.setStartPointName(tourLocationDto.getStartPoint().getName());
		entity.setStartPointCoor(tourLocationDto.getStartPoint().getCoor());
		
		entity.setEndPointName(tourLocationDto.getEndPoint().getName());
		entity.setEndPointCoor(tourLocationDto.getEndPoint().getCoor());
		entity.setEndPointDist(tourLocationDto.getEndPoint().getDist());
		
		entity.setTour(tour);
		
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
