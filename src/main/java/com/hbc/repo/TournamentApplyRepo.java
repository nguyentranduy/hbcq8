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
	@Query(value = "INSERT INTO tournament_apply(bird_code, tour_id, requester_id, created_at, created_by, status_code)"
			+ "VALUE (:birdCode, :tourId, :requesterId, :createdAt, :createdBy, :statusCode)", nativeQuery = true)
	void doRegister(@Param("birdCode") String birdCode, @Param("tourId") long tourId,
			@Param("requesterId") long requesterId, @Param("createdAt") Timestamp createdAt,
			@Param("createdBy") long createdBy, @Param("statusCode") String statusCode);
	
	TournamentApply findByTourId(long tourId);
	
	boolean existsByBirdCodeAndTourId(String birdCode, long tourId);
	boolean existsByBirdCodeAndRequesterIdAndStatusCodeNot(String birdCode, long requesterId, String statusCode);
	boolean existsByTourIdAndRequesterId(long tourId, long requesterId);
	
	int countByTourIdAndRequesterId(long tourId, long requesterId);
	
	List<TournamentApply> findByTourIdAndRequesterIdAndBirdCodeIn(long tourId, long requesterId, List<String> birdCodes);
	
	@Query(value = "SELECT tour_id, GROUP_CONCAT(bird_code) AS birdCodes, requester_id, approver_id,"
			+ " status_code, memo, created_at"
			+ " FROM tournament_apply"
			+ " WHERE tour_id = :tourId"
			+ " GROUP BY requester_id, approver_id, memo, created_at, status_code, tour_id", nativeQuery = true)
	List<Object[]> findCustomByTourId(@Param("tourId") long tourId);
	
	@Query(value = "SELECT tour_id, GROUP_CONCAT(bird_code) AS birdCodes, requester_id, approver_id,"
			+ " status_code, memo, created_at"
			+ " FROM tournament_apply"
			+ " WHERE tour_id = :tourId AND requester_id = :requesterId"
			+ " GROUP BY requester_id, approver_id, memo, created_at, status_code, tour_id", nativeQuery = true)
	List<Object[]> findCustomByTourIdAndRequesterId(@Param("tourId") long tourId,
			@Param("requesterId") long requesterId);
	
	@Modifying
	@Query(value = "UPDATE tournament_apply SET status_code = :statusCode, memo = :memo,"
			+ " approver_id = :approverId, updated_by = :updatedBy, updated_at = :updatedAt"
			+ " WHERE tour_id = :tourId AND requester_id = :requesterId", nativeQuery = true)
	void doUpdate(@Param("statusCode") String statusCode, @Param("memo") String memo, @Param("approverId") long approverId,
			@Param("updatedBy") long updatedBy, @Param("updatedAt") Timestamp updatedAt,
			@Param("tourId") long tourId, @Param("requesterId") long requesterId);
	
	int deleteByTourIdAndRequesterId(long tourId, long requesterId);
	
	@Query(value = "SELECT status_code FROM tournament_apply"
			+ " WHERE tour_id = :tourId AND requester_id = :requesterId"
			+ " GROUP BY status_code, tour_id, requester_id", nativeQuery = true)
	String findStatusCodeByTourIdAndRequesterId(@Param("tourId") long tourId, @Param("requesterId") long requesterId);
	
	@Query(value = "SELECT memo FROM tournament_apply"
			+ " WHERE tour_id = :tourId AND requester_id = :requesterId"
			+ " GROUP BY memo, tour_id, requester_id", nativeQuery = true)
	String findMemoByTourIdAndRequesterId(@Param("tourId") long tourId, @Param("requesterId") long requesterId);

	@Query(value = "SELECT bird_code FROM tournament_apply"
			+ " WHERE tour_id = :tourId", nativeQuery = true)
	List<String> findBirdCodeById(@Param("tourId") long tourId);
}
