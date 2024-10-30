package com.hbc.repo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);
	Optional<User> findByIdAndIsDeleted(long id, Boolean isDeleted);
	
	Boolean existsByIdAndIsDeleted(Long userId, Boolean isDeleted);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByPhone(String phone);
	Optional<User> findByPhoneAndIdNot(String phone, long userId);

	@Modifying
	@Query("UPDATE User u SET u.imgUrl = :imgUrl WHERE u.username = :username")
	Boolean updateimgUrlByUsername(@Param("imgUrl") String imgUrl, @Param("username") String username);
	
	@Modifying
	@Query("UPDATE User u SET u.phone = :phone, u.address = :address,"
			+ "u.birthday = :birthday, u.imgUrl = :imgUrl,"
			+ "u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :id")
	int update(@Param("phone") String phone, @Param("address") String address,
			@Param("birthday") Date birthday, @Param("imgUrl") String imgUrl,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") Long updatedBy,
			@Param("id") Long id);
	
	@Modifying
	@Query("UPDATE User u SET u.username = :username, u.phone = :phone, u.address = :address,"
			+ "u.birthday = :birthday, u.imgUrl = :imgUrl,"
			+ "u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :id")
	int updateIncludeUsername(@Param("username") String username, @Param("phone") String phone,
			@Param("address") String address, @Param("birthday") Date birthday, @Param("imgUrl") String imgUrl,
			@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") Long updatedBy,
			@Param("id") Long id);
	
	@Modifying
	@Query("UPDATE User u SET u.isDeleted = true, u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :id")
	void deleteLogical(@Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") Long updatedBy,
			@Param("id") Long id);
	
	@Query(value = "SELECT username FROM user WHERE id = :userId", nativeQuery = true)
	String findUserNameById(@Param("userId") long userId);
	
	List<User> findByIsDeletedOrderByUsernameAsc(boolean isDeleted);
}
