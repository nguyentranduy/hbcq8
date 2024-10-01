package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Tournament;

@Repository
public interface TournamentRepo extends JpaRepository<Tournament, Long> {

	List<Tournament> findByIsActived(boolean isActived);
	Tournament findByIdAndIsActived(long tourId, boolean isActived);
	boolean existsByName(String tourName);
	boolean existsByNameAndIdNot(String tourName, long tourId);
	
	@Query(value = "SELECT t.id, t.name, t.start_date, t.end_date, l.start_point_name,"
			+ " l.end_point_name, t.birds_num, t.is_actived"
			+ " FROM tournament t"
			+ " LEFT JOIN tournament_location l ON t.id = l.tour_id"
			+ " ORDER BY t.created_at desc", nativeQuery = true)
	List<Object[]> getTournamentInfo();
	
	@Modifying
	@Query(value = "UPDATE Tournament t SET t.name = :name, t.birdsNum = :birdsNum, t.startDate = :startDate,"
			+ " t.endDate = :endDate, t.restTimePerDay = :restTimePerDay, t.isActived = :isActived,"
			+ " t.updatedAt = :updatedAt, t.updatedBy = :updatedBy"
			+ " WHERE t.id = :id")
	int update(@Param("name") String name, @Param("birdsNum") int birdsNum, @Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, @Param("restTimePerDay") float restTimePerDay,
			@Param("isActived") boolean isActived, @Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") Long updatedBy, @Param("id") long id);
}
