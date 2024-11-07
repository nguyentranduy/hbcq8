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
	List<TournamentDetail> findByTour_IdAndStatus(long tourId, String status);
	TournamentDetail findByTour_IdAndBird_Code(long tourId, String birdCode);

	@Modifying
	@Query(value = "INSERT INTO tournament_detail(user_id, bird_code, tour_id, "
			+ "start_point_code, start_point_coor, point1_code, point1_coor, point1_dist, "
			+ "point2_code, point2_coor, point2_dist, "
			+ "point3_code, point3_coor, point3_dist, "
			+ "point4_code, point4_coor, point4_dist, "
			+ "point5_code, point5_coor, point5_dist, "
			+ "end_point_code, end_point_coor, end_point_dist, "
			+ "created_at, created_by) "
			+ "VALUE (:userId, :birdCode, :tourId, :startPointCode, :startPointCoor, "
			+ ":point1Code, :point1Coor, :point1Dist, :point2Code, :point2Coor, :point2Dist, "
			+ ":point3Code, :point3Coor, :point3Dist, :point4Code, :point4Coor, :point4Dist, "
			+ ":point5Code, :point5Coor, :point5Dist, "
			+ ":endPointCode, :endPointCoor, :endPointDist, "
			+ ":createdAt, :createdBy)", nativeQuery = true)
	void doRegister(@Param("userId") long userId, @Param("birdCode") String birdCode, @Param("tourId") long tourId,
			@Param("startPointCode") String startPointCode, @Param("startPointCoor") String startPointCoor,
			@Param("point1Code") String point1Code, @Param("point1Coor") String point1Coor, @Param("point1Dist") double point1Dist,
			@Param("point2Code") String point2Code, @Param("point2Coor") String point2Coor, @Param("point2Dist") double point2Dist,
			@Param("point3Code") String point3Code, @Param("point3Coor") String point3Coor, @Param("point3Dist") double point3Dist,
			@Param("point4Code") String point4Code, @Param("point4Coor") String point4Coor, @Param("point4Dist") double point4Dist,
			@Param("point5Code") String point5Code, @Param("point5Coor") String point5Coor, @Param("point5Dist") double point5Dist,
			@Param("endPointCode") String endPointCode, @Param("endPointCoor") String endPointCoor, @Param("endPointDist") double endPointDist,
			@Param("createdAt") Timestamp createdAt, @Param("createdBy") long createdBy);

	@Modifying
	@Query(value = "DELETE FROM tournament_detail WHERE tour_id = :tourId AND user_id = :userId", nativeQuery = true)
	void doDeleteByTourIdAndUserId(@Param("tourId") long tourId, @Param("userId") long userId);

	TournamentDetail findByTourIdAndUserIdAndBirdCode(long tourId, long userId, String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point1_key = :point1Key, point1_time = :point1Time,"
			+ " point1_submit_time = :point1SubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdatePoint1(@Param("point1Key") String point1Key, @Param("point1Time") Timestamp point1Time,
			@Param("point1SubmitTime") Timestamp point1SubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point2_key = :point2Key, point2_time = :point2Time,"
			+ " point2_submit_time = :point2SubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdatePoint2(@Param("point2Key") String point2Key, @Param("point2Time") Timestamp point2Time,
			@Param("point2SubmitTime") Timestamp point2SubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point3_key = :point3Key, point3_time = :point3Time,"
			+ "point3_submit_time = :point3SubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdatePoint3(@Param("point3Key") String point3Key, @Param("point3Time") Timestamp point3Time,
			@Param("point3SubmitTime") Timestamp point3SubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point4_key = :point4Key, point4_time = :point4Time,"
			+ "point4_submit_time = :point4SubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdatePoint4(@Param("point4Key") String point4Key, @Param("point4Time") Timestamp point4Time,
			@Param("point4SubmitTime") Timestamp point4SubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point5_key = :point5Key, point5_time = :point5Time,"
			+ "point5_submit_time = :point5SubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdatePoint5(@Param("point5Key") String point5Key, @Param("point5Time") Timestamp point5Time,
			@Param("point5SubmitTime") Timestamp point5SubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET end_point_key = :endPointKey, end_point_time = :endPointTime,"
			+ "end_point_submit_time = :endPointSubmitTime, status = 'W'"
			+ " WHERE tour_id = :tourId AND user_id = :userId AND bird_code = :birdCode", nativeQuery = true)
	void doUpdateEndPoint(@Param("endPointKey") String endPointKey, @Param("endPointTime") Timestamp endPointTime,
			@Param("endPointSubmitTime") Timestamp endPointSubmitTime,
			@Param("tourId") long tourId, @Param("userId") long userId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET point1_speed = :point1Speed, point2_speed = :point2Speed, "
			+ "point3_speed = :point3Speed, point4_speed = :point4Speed, point5_speed = :point5Speed, "
			+ "end_point_speed = :endPointSpeed, avg_speed = :avgSpeed, "
			+ "updated_at = :updatedAt, updated_by = :updatedBy, status = 'A' "
			+ "WHERE tour_id = :tourId AND bird_code = :birdCode", nativeQuery = true)
	void doApproveResult(@Param("point1Speed") double point1Speed, @Param("point2Speed") double point2Speed,
			@Param("point3Speed") double point3Speed, @Param("point4Speed") double point4Speed,
			@Param("point5Speed") double point5Speed, @Param("endPointSpeed") double endPointSpeed,
			@Param("avgSpeed") double avgSpeed, @Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") long updatedBy,
			@Param("tourId") long tourId, @Param("birdCode") String birdCode);

	@Modifying
	@Query(value = "UPDATE tournament_detail SET rank_of_bird = :rank WHERE tour_id = :tourId AND bird_code = :birdCode", nativeQuery = true)
	void sortRankByTourId(@Param("rank") int rank, @Param("tourId") long tourId, @Param("birdCode") String birdCode);

	@Query(value = "SELECT rank_of_bird, bird_code, avg_speed FROM tournament_detail WHERE tour_id = :tourId AND status = 'A'"
			+ " ORDER BY rank_of_bird ASC", nativeQuery = true)
	List<Object[]> viewRankByTourId(@Param("tourId") long tourId);
}
