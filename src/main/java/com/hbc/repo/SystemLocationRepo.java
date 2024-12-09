package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.SystemLocation;

@Repository
public interface SystemLocationRepo extends JpaRepository<SystemLocation, Long> {

	List<SystemLocation> findByIsDeleted(boolean isDeleted);
	SystemLocation findByCodeAndIsDeleted(String code, boolean isDeleted);
	SystemLocation findByIdAndIsDeleted(long id, boolean isDeleted);
	boolean existsByCodeAndIsDeleted(String code, boolean isDeleted);

	@Modifying
	@Query(value = "INSERT INTO system_location(code, name, point_coor, created_at, created_by) "
			+ "VALUE (:code, :name, :pointCoor, :createdAt, :createdBy)", nativeQuery = true)
	void doRegister(@Param("code") String code, @Param("name") String name,
			@Param("pointCoor") String pointCoor, @Param("createdAt") Timestamp createdAt,
			@Param("createdBy") long createdBy);

	@Modifying
	@Query(value = "UPDATE system_location SET code = :code, name = :name, point_coor = :pointCoor, "
			+ "updated_at = :updatedAt, updated_by = :updatedBy "
			+ "WHERE id = :id", nativeQuery = true)
	void doUpdate(@Param("code") String code, @Param("name") String name, @Param("pointCoor") String pointCoor,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy,
			@Param("id") long id);

	@Modifying
	@Query(value = "UPDATE system_location SET is_deleted = true, updated_at = :updatedAt, updated_by = :updatedBy "
			+ "WHERE id = :id", nativeQuery = true)
	void doLogicalDelete(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("id") long id);
}
