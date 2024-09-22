package com.hbc.repo;

import java.sql.Date;
import java.sql.Timestamp;
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

	Boolean existsByIdAndIsDeleted(Long userId, Boolean isDeleted);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	@Modifying
	@Query("UPDATE User u SET u.imgUrl = :imgUrl WHERE u.username = :username")
	Boolean updateimgUrlByUsername(@Param("imgUrl") String imgUrl, @Param("username") String username);

	@Modifying
	@Query("UPDATE User u SET u.phone = :phone, u.address = :address," + "u.birthday = :birthday, u.imgUrl = :imgUrl,"
			+ "u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :id")
	int update(@Param("phone") String phone, @Param("address") String address, @Param("birthday") Date birthday,
			@Param("imgUrl") String imgUrl, @Param("updatedAt") Timestamp updatedAt, @Param("updatedBy") Long updatedBy,
			@Param("id") Long id);
}
