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
	boolean existsByTourIdAndIsActived(long tourId, boolean isActive);
	boolean existsByIdAndIsFinished(long id, boolean isFinished);
	
	@Modifying
	@Query(value = "INSERT INTO tournament_stage(tour_id, order_no, description, rest_time_per_day,"
			+ " start_point_code, start_point_name, start_point_coor, start_time, created_at, created_by)"
			+ " VALUE (:tourId, :orderNo, :description, :restTimePerDay, :startPointCode,"
			+ " :startPointName, :startPointCoor, :startTime, :createdAt, :createdBy)", nativeQuery = true)
	void insert(@Param("tourId") long tourId, @Param("orderNo") int orderNo, @Param("description") String description,
			@Param("restTimePerDay") float restTimePerDay, @Param("startPointCode") String startPointCode,
			@Param("startPointName") String startPointName, @Param("startPointCoor") String startPointCoor,
			@Param("startTime") Timestamp startTime, @Param("createdAt") Timestamp createdAt, @Param("createdBy") long createdBy);
	
	@Modifying
	@Query(value = "UPDATE tournament_stage SET description = :description, rest_time_per_day = :restTimePerDay,"
			+ " start_point_code = :startPointCode, start_point_name = :startPointName, start_point_coor = :startPointCoor"
			+ " start_time = :startTime, updated_at = updatedAt:, updated_by = :updatedBy"
			+ " WHERE tour_id = :tourId AND order_no = :orderNo", nativeQuery = true)
	void update(@Param("description") String description, @Param("restTimePerDay") float restTimePerDay,
			@Param("startPointCode") String startPointCode, @Param("startPointName") String startPointName,
			@Param("startPointCoor") String startPointCoor, @Param("startTime") Timestamp startTime,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy,
			@Param("tourId") long tourId, @Param("orderNo") int orderNo);
	
	@Modifying
	@Query(value = "DELETE FROM tournament_stage WHERE tour_id = :tourId", nativeQuery = true)
	void deleteByTourId(@Param("tourId") long tourId);
	
	@Modifying
	@Query(value = "UPDATE tournament_stage SET is_actived = :isActived, updated_at = :updatedAt,"
			+ " updated_by = :updatedBy WHERE id = :stageId", nativeQuery = true)
	void updateStatus(@Param("isActived") boolean isActived, @Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") long updatedBy, @Param("stageId") long stageId);
	
	@Modifying
	@Query(value = "UPDATE tournament_stage SET is_finished = true, updated_at = :updatedAt,"
			+ " updated_by = :updatedBy WHERE id = :stageId", nativeQuery = true)
	void updateFinished(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("stageId") long stageId);
}
