package com.hbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentLocation;

@Repository
public interface TournamentLocationRepo extends JpaRepository<TournamentLocation, Long> {

	Optional<TournamentLocation> findByTourId(long tourId);
	boolean existsByTourId(long tourId);
	TournamentLocation findByIdAndTourId(long id, long tourId);
}
