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

	List<Tournament> findByIsDeletedAndIsActivedOrderByCreatedAtDesc(boolean isDeleted, boolean isActived);
	List<Tournament> findByIdInAndIsDeletedAndIsActivedOrderByCreatedAtDesc(
			List<Long> tourIds, boolean isDeleted, boolean isActived);
	List<Tournament> findByIsDeletedOrderByCreatedAtDesc(boolean isDeleted);
	boolean existsByIdAndIsActived(long id, boolean isActived);
	boolean existsByNameAndIsDeleted(String tourName, boolean isDeleted);
	boolean existsByIdAndIsDeleted(long id, boolean isDeleted);
	boolean existsByNameAndIdNotAndIsDeleted(String tourName, long tourId, boolean isDeleted);
	
	@Modifying
	@Query(value = "UPDATE Tournament t SET t.name = :name, t.birdsNum = :birdsNum, t.startDate = :startDate,"
			+ " t.endDate = :endDate, t.restTimePerDay = :restTimePerDay,"
			+ " t.startPointCode = :startPointCode, t.startPointCoor = :startPointCoor, t.startPointTime = :startPointTime,"
			+ " t.endPointCode = :endPointCode, t.endPointCoor = :endPointCoor,"
			+ " t.isActived = :isActived, t.updatedAt = :updatedAt, t.updatedBy = :updatedBy"
			+ " WHERE t.id = :id")
	int update(@Param("name") String name, @Param("birdsNum") int birdsNum, @Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, @Param("restTimePerDay") float restTimePerDay,
			@Param("startPointCode") String startPointCode, @Param("startPointCoor") String startPointCoor,
			@Param("startPointTime") Timestamp startPointTime,
			@Param("endPointCode") String endPointCode, @Param("endPointCoor") String endPointCoor,
			@Param("isActived") boolean isActived, @Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") Long updatedBy, @Param("id") long id);

	@Query(value = "SELECT birds_num FROM tournament WHERE id = :tourId", nativeQuery = true)
	int findBirdsNumById(@Param("tourId") long tourId);
	
	@Modifying
	@Query(value = "UPDATE Tournament t SET t.isDeleted = :isDeleted, t.isActived = :isActived WHERE t.id = :id")
	int deleteLogical(@Param("isDeleted") boolean isDeleted, @Param("isActived") boolean isActived, @Param("id") long id);
}
