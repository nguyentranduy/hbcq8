package com.hbc.repo;

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
	List<Bird> findByUserId(long userId);
	Bird findByCode(String birdCode);
	
	@Modifying
	@Query(value = "INSERT INTO bird(name, code, user_id, img_url, created_by)"
			+ " VALUE (:name, :code, :userId, :imgUrl, :createdBy)", nativeQuery = true)
	void doRegister(@Param("name") String name, @Param("code") String code, @Param("userId") long userId,
			@Param("imgUrl") String imgUrl, @Param("createdBy") long createdBy);
}
