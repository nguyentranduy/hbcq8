package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentStage;

@Repository
public interface TournamentStageRepo extends JpaRepository<TournamentStage, Long> {

	List<TournamentStage> findByTourId(long tourId);
	
	@Modifying
	@Query(value = "INSERT INTO tournament_stage(tour_id, order_no, description, rest_time_per_day,"
			+ " start_point_code, start_point_name, start_point_coor, start_time, created_at, created_by)"
			+ " VALUE (:tourId, :orderNo, :description, :restTimePerDay, :startPointCode,"
			+ " :startPointName, :startPointCoor, :startTime, :createdAt, :createdBy)", nativeQuery = true)
	void insert(@Param("tourId") long tourId, @Param("orderNo") int orderNo, @Param("description") String description,
			@Param("restTimePerDay") float restTimePerDay, @Param("startPointCode") String startPointCode,
			@Param("startPointName") String startPointName, @Param("startPointCoor") String startPointCoor,
			@Param("startTime") Timestamp startTime, @Param("createdAt") Timestamp createdAt, @Param("createdBy") long createdBy);
}
