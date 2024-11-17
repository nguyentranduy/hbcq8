package com.hbc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentStage;

@Repository
public interface TournamentStageRepo extends JpaRepository<TournamentStage, Long> {

	List<TournamentStage> findByTourId(long tourId);
}
