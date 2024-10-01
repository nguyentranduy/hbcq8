package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Query(value = "UPDATE tournament_location SET start_point_name = :startPointName, start_point_coor = :startPointCoor,"
			+ " point1_name = :point1Name, point1_coor = :point1Coor, point1_dist = :point1Dist,"
			+ " point2_name = :point2Name, point2_coor = :point2Coor, point2_dist = :point2Dist,"
			+ " point3_name = :point3Name, point3_coor = :point3Coor, point3_dist = :point3Dist,"
			+ " point4_name = :point4Name, point4_coor = :point4Coor, point4_dist = :point4Dist,"
			+ " point5_name = :point5Name, point5_coor = :point5Coor, point5_dist = :point5Dist,"
			+ " end_point_name = :endPointName, end_point_coor = :endPointCoor, end_point_dist = :endPointDist,"
			+ " updated_at = :updatedAt, updated_by = :updatedBy"
			+ " WHERE tour_id = :tourId", nativeQuery = true)
	void update(@Param("startPointName") String startPointName, @Param("startPointCoor") String startPointCoor,
			@Param("point1Name") String point1Name, @Param("point1Coor") String point1Coor, @Param("point1Dist") float point1Dist,
			@Param("point2Name") String point2Name, @Param("point2Coor") String point2Coor, @Param("point2Dist") float point2Dist,
			@Param("point3Name") String point3Name, @Param("point3Coor") String point3Coor, @Param("point3Dist") float point3Dist,
			@Param("point4Name") String point4Name, @Param("point4Coor") String point4Coor, @Param("point4Dist") float point4Dist,
			@Param("point5Name") String point5Name, @Param("point5Coor") String point5Coor, @Param("point5Dist") float point5Dist,
			@Param("endPointName") String endPointName, @Param("endPointCoor") String endPointCoor, @Param("endPointDist") float endPointDist,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("tourId") long tourId);
}
