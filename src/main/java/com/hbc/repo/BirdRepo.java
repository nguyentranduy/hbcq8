package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Bird;
import com.hbc.entity.User;
import com.hbc.exception.AuthenticationException;

@Repository
public interface BirdRepo extends JpaRepository<Bird, Long> {

	boolean existsById(Long birdId);

	boolean existsByBirdSecretKey(String birdSecretKey);

	@Modifying
	@Query("UPDATE Bird b SET b.imgUrl = :imgUrl WHERE b.birdSecretKey = :birdSecretKey")
	int updateimgUrlById(@Param("imgUrl") String imgUrl, @Param("birdSecretKey") String birdSecretKey) throws AuthenticationException;

	@Modifying
	@Query("UPDATE Bird b SET b.name = :name, b.updatedBy = :updateBy, " + "b.updatedAt = :updateAt WHERE b.id = :id")
	int update(@Param("name") String name, @Param("updateBy") Long updateBy, @Param("updateAt") Timestamp updateAt,
			@Param("id") Long id) throws AuthenticationException;

	List<Bird> findByUser(User user);

	Bird findByBirdSecretKey(String birdSecretKey);
}
