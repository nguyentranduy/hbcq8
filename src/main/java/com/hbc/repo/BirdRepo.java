package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Bird;

@Repository
public interface BirdRepo extends JpaRepository<Bird, Long> {

	boolean existsByCode(String birdCode);
	boolean existsByCodeAndIsDeletedAndIdNot(String birdCode, boolean isDeleted, long id);
	boolean existsByCodeAndIsDeleted(String birdCode, boolean isDeleted);
	boolean existsByCodeAndUserId(String birdCode, long userId);
	List<Bird> findByUserIdAndIsDeleted(long userId, boolean isDeleted);
	Bird findByCode(String birdCode);
	
	@Modifying
	@Query(value = "INSERT INTO bird(name, code, user_id, created_by)"
			+ " VALUE (:name, :code, :userId, :createdBy)", nativeQuery = true)
	void doRegister(@Param("name") String name, @Param("code") String code,
			@Param("userId") long userId, @Param("createdBy") long createdBy);
	
	@Modifying
	@Query(value = "UPDATE bird SET code = :code, name = :name, description = :description, img_url = :imgUrl,"
			+ " updated_by = :updatedBy, updated_at = :updatedAt"
			+ " WHERE id = :id", nativeQuery = true)
	void doUpdate(@Param("code") String code, @Param("name") String name, @Param("description") String description,
			@Param("imgUrl") String imgUrl, @Param("updatedBy") long updatedBy,
			@Param("updatedAt") Timestamp updatedAt, @Param("id") long id);
	
	@Modifying
	@Query(value = "UPDATE bird SET name = :name, description = :description, img_url = :imgUrl,"
			+ " updated_by = :updatedBy, updated_at = :updatedAt"
			+ " WHERE id = :id", nativeQuery = true)
	void doUserUpdate(@Param("name") String name, @Param("description") String description,
			@Param("imgUrl") String imgUrl, @Param("updatedBy") long updatedBy,
			@Param("updatedAt") Timestamp updatedAt, @Param("id") long id);

	@Modifying
	@Query(value = "UPDATE bird SET is_deleted = 1, updated_by = :updatedBy, updated_at = :updatedAt"
			+ " WHERE code = :code", nativeQuery = true)
	void deleteByCode(@Param("updatedBy") long updatedBy, @Param("updatedAt") Timestamp updatedAt,
			@Param("code") String code);
}
