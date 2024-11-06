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
}
