package com.hbc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Tournament;

@Repository
public interface TournamentRepo extends JpaRepository<Tournament, Long> {

	List<Tournament> findByIsActived(boolean isActived);
	Tournament findByIdAndIsActived(long tourId, boolean isActived);
	boolean existsByName(String tourName);
	
	@Query(value = "SELECT t.id, t.name, t.start_date, t.end_date, l.start_point_name,"
			+ " l.end_point_name, t.birds_num, t.is_actived"
			+ " FROM tournament t"
			+ " LEFT JOIN tournament_location l ON t.id = l.tour_id"
			+ " ORDER BY t.created_at desc", nativeQuery = true)
	List<Object[]> getTournamentInfo();
}
