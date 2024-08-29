package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentLocation;

@Repository
public interface TournamentLocationRepo extends JpaRepository<TournamentLocation, Long> {

	TournamentLocation findByTourId(long tourId);
}
