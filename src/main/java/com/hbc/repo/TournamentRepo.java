package com.hbc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Tournament;

@Repository
public interface TournamentRepo extends JpaRepository<Tournament, Long> {

	List<Tournament> findByIsActived(boolean isActived);
	Tournament findByIdAndIsActived(long tourId, boolean isActived);
	boolean existsByName(String tourName);
}
