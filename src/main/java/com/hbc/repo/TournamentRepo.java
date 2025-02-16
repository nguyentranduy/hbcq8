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

	List<Tournament> findByIdInAndIsDeletedOrderByCreatedAtDesc(
			List<Long> tourIds, boolean isDeleted);
	List<Tournament> findByIsDeletedOrderByCreatedAtDesc(boolean isDeleted);
	boolean existsByNameAndIsDeleted(String tourName, boolean isDeleted);
	boolean existsByIdAndIsDeleted(long id, boolean isDeleted);
	boolean existsByIdAndIsFinished(long id, boolean isFinished);
	
	@Modifying
	@Query(value = "UPDATE Tournament t SET t.name = :name, t.description = :description,"
			+ " t.startDateInfo = :startDateInfo, t.endDateInfo = :endDateInfo,"
			+ " t.startDateReceive = :startDateReceive, t.endDateReceive = :endDateReceive,"
			+ " t.updatedAt = :updatedAt, t.updatedBy = :updatedBy"
			+ " WHERE t.id = :id")
	int update(@Param("name") String name, @Param("description") String description,
			@Param("startDateInfo") Timestamp startDateInfo, @Param("endDateInfo") Timestamp endDateInfo,
			@Param("startDateReceive") Timestamp startDateReceive, @Param("endDateReceive") Timestamp endDateReceive,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") Long updatedBy, @Param("id") long id);

	@Modifying
	@Query(value = "UPDATE Tournament t SET t.isDeleted = :isDeleted WHERE t.id = :id")
	int deleteLogical(@Param("isDeleted") boolean isDeleted, @Param("id") long id);
	
	@Modifying
	@Query(value = "UPDATE tournament SET is_finished = true, updated_at = :updatedAt, updated_by = :updatedBy"
			+ " WHERE id = :id", nativeQuery = true)
	void doFinishedTour(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("id") long id);
}
