package com.hbc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentLocation;

@Repository
public interface TournamentLocationRepo extends JpaRepository<TournamentLocation, Long> {

	Optional<TournamentLocation> findByTourId(long tourId);
	@Query(value = "SELECT * FROM tournament_location WHERE tour_id IN :tourIds", nativeQuery = true)
	List<TournamentLocation> findListByTourIds(@Param("tourIds") List<Long> tourIds);
	boolean existsByTourId(long tourId);
	TournamentLocation findByIdAndTourId(long id, long tourId);
}
