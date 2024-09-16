package com.hbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByPhone(String phone);

	@Query("UPDATE User u SET u.imgUrl = :imgUrl WHERE u.username = :username")
	Boolean updateimgUrlByUsername(@Param("imgUrl") String imgUrl, @Param("username") String username);
}
