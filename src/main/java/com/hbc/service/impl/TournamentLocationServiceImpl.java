package com.hbc.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hbc.dto.PagedResponse;
import com.hbc.dto.Pagination;
import com.hbc.dto.tourlocation.TourLocationDto;
import com.hbc.entity.TournamentLocation;
import com.hbc.repo.TournamentLocationRepo;
import com.hbc.service.TournamentLocationService;

@Service
public class TournamentLocationServiceImpl implements TournamentLocationService {
	
	@Autowired
	TournamentLocationRepo repo;

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
}
