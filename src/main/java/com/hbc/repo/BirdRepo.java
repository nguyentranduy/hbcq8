package com.hbc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Bird;

@Repository
public interface BirdRepo extends JpaRepository<Bird, Long> {

	boolean existsByCode(String birdCode);
	List<Bird> findByUserId(long userId);
}
