package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.UserLocation;

@Repository
public interface UserLocationRepo extends JpaRepository<UserLocation, Long> {

	List<UserLocation> findByUser_IdAndIsDeletedOrderByCreatedAtAsc(long userId, boolean isDeleted);
	UserLocation findByIdAndUser_IdAndIsDeleted(long id, long userId, boolean isDeleted);
	UserLocation findByCodeAndIsDeleted(String code, boolean isDeleted);

	@Query("SELECT u.pointCoor FROM UserLocation u WHERE u.code = :code")
	String findPointCoorByCode(String code);

	@Modifying
	@Query(value = "INSERT INTO user_location(code, user_id, point_coor, created_at, created_by) "
			+ "VALUE (:code, :userId, :pointCoor, :createdAt, :createdBy)", nativeQuery = true)
	void doRegister(@Param("code") String name, @Param("userId") long userId,
			@Param("pointCoor") String pointCoor, @Param("createdAt") Timestamp createdAt,
			@Param("createdBy") long createdBy);

	@Modifying
	@Query(value = "UPDATE user_location SET code = :code, point_coor = :pointCoor, "
			+ "updated_at = :updatedAt, updated_by = :updatedBy "
			+ "WHERE id = :id", nativeQuery = true)
	void doUpdate(@Param("code") String code, @Param("pointCoor") String pointCoor,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy,
			@Param("id") long id);

	@Modifying
	@Query(value = "UPDATE user_location SET is_deleted = true, updated_at = :updatedAt, updated_by = :updatedBy "
			+ "WHERE id = :id", nativeQuery = true)
	void doLogicalDelete(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") long updatedBy, @Param("id") long id);

	@Modifying
	@Query(value = "UPDATE user_location SET is_deleted = true, updated_at = :updatedAt, updated_by = :updatedBy "
			+ "WHERE code = :code", nativeQuery = true)
	void doLogicalDeleteByCode(@Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") long updatedBy, @Param("code") String code);

	boolean existsByCodeAndIsDeleted(String code, boolean isDeleted);
	boolean existsByCodeAndUserIdAndIsDeleted(String code, long userId, boolean isDeleted);
}
