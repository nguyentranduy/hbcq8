package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentDetail;

@Repository
public interface TournamentDetailRepo extends JpaRepository<TournamentDetail, Long> {
	
	List<TournamentDetail> findByTour_IdAndUser_Id(long tourId, long userId);
	List<TournamentDetail> findByTour_IdAndTourStage_IdAndStatusNotNullOrderByStatusAsc(long tourId, long stageId);
	TournamentDetail findByTour_IdAndBird_Code(long tourId, String birdCode);

	@Modifying
	@Query(value = "INSERT INTO tournament_detail(user_id, bird_code, tour_id, stage_id, "
			+ "end_point_code, end_point_coor, end_point_dist, "
			+ "created_at, created_by) "
			+ "VALUE (:userId, :birdCode, :tourId, :stageId, :endPointCode, :endPointCoor, :endPointDist, "
			+ ":createdAt, :createdBy)", nativeQuery = true)
	void doRegister(@Param("userId") long userId, @Param("birdCode") String birdCode, @Param("tourId") long tourId,
			@Param("stageId") long stageId, @Param("endPointCode") String endPointCode, @Param("endPointCoor") String endPointCoor,
			@Param("endPointDist") double endPointDist, @Param("createdAt") Timestamp createdAt, @Param("createdBy") long createdBy);

	@Modifying
	@Query(value = "DELETE FROM tournament_detail WHERE tour_id = :tourId AND user_id = :userId", nativeQuery = true)
	void doDeleteByTourIdAndUserId(@Param("tourId") long tourId, @Param("userId") long userId);

	TournamentDetail findByTourIdAndUserIdAndBirdCode(long tourId, long userId, String birdCode);
	TournamentDetail findByTourIdAndUserIdAndBirdCodeAndTourStage_Id(long tourId, long userId, String birdCode, long stageId);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET end_point_key = :endPointKey, end_point_time = :endPointTime,"
			+ " end_point_submit_time = :endPointSubmitTime, end_point_speed = :endPointSpeed, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode"
			+ " AND stage_id = :stageId", nativeQuery = true)
	void doUpdateEndPoint(@Param("endPointKey") String endPointKey, @Param("endPointTime") Timestamp endPointTime,
			@Param("endPointSubmitTime") Timestamp endPointSubmitTime, @Param("endPointSpeed") double endPointSpeed,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode,
			@Param("stageId") long stageId);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET updated_at = :updatedAt, updated_by = :updatedBy, status = 'A' "
			+ "WHERE tour_id = :tourId AND bird_code = :birdCode AND stage_id = :stageId", nativeQuery = true)
	void doApproveResult(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy,
			@Param("tourId") long tourId, @Param("birdCode") String birdCode, @Param("stageId") long stageId);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET updated_at = :updatedAt, updated_by = :updatedBy, status = 'R', memo = :memo "
			+ "WHERE tour_id = :tourId AND bird_code = :birdCode AND stage_id = :stageId", nativeQuery = true)
	void doRejectResult(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("memo") String memo, 
			@Param("tourId") long tourId, @Param("birdCode") String birdCode, @Param("stageId") long stageId);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET updated_at = :updatedAt, updated_by = :updatedBy, status = 'W' "
			+ "WHERE tour_id = :tourId AND bird_code = :birdCode AND stage_id = :stageId", nativeQuery = true)
	void doCancelResult(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, 
			@Param("tourId") long tourId, @Param("birdCode") String birdCode, @Param("stageId") long stageId);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET rank_of_bird = :rank WHERE tour_id = :tourId AND bird_code = :birdCode", nativeQuery = true)
	void sortRankByTourId(@Param("rank") int rank, @Param("tourId") long tourId, @Param("birdCode") String birdCode);

	@Query(value = "SELECT rank_of_bird, bird_code, avg_speed FROM tournament_detail WHERE tour_id = :tourId AND status = 'A'"
			+ " ORDER BY rank_of_bird ASC", nativeQuery = true)
	List<Object[]> viewRankByTourId(@Param("tourId") long tourId);

	List<TournamentDetail> findByTourStage_IdAndTour_IdAndStatusNotNullOrderByEndPointSpeedDesc(long stageId, long tourId);
}
