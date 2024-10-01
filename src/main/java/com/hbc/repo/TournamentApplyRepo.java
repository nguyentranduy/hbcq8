package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentApply;

@Repository
public interface TournamentApplyRepo extends JpaRepository<TournamentApply, Long> {

	@Modifying
	@Query(value = "INSERT INTO tournament_apply(bird_code, tour_id, requester_id, created_at, created_by, is_bird_applied)"
			+ "VALUE (:birdCode, :tourId, :requesterId, :createdAt, :createdBy, 0)", nativeQuery = true)
	void doRegister(@Param("birdCode") String birdCode, @Param("tourId") long tourId,
			@Param("requesterId") long requesterId, @Param("createdAt") Timestamp createdAt,
			@Param("createdBy") long createdBy);
	
	TournamentApply findByTourId(long tourId);
	
	boolean existsByBirdCodeAndTourId(String birdCode, long tourId);
	
	List<TournamentApply> findByTourIdAndRequesterIdAndBirdCodeIn(long tourId, long requesterId, List<String> birdCodes);
	
	@Query(value = "SELECT tour_id, GROUP_CONCAT(bird_code) AS birdCodes, requester_id, approver_id,"
			+ " is_bird_applied, memo, created_at"
			+ " FROM tournament_apply"
			+ " WHERE tour_id = :tourId"
			+ " GROUP BY requester_id, approver_id, memo, created_at, is_bird_applied, tour_id", nativeQuery = true)
	List<Object[]> findCustomByTourId(@Param("tourId") long tourId);
}
