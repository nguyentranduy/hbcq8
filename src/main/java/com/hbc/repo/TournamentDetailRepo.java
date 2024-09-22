package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Bird;
import com.hbc.entity.TournamentDetail;

@Repository
public interface TournamentDetailRepo extends JpaRepository<TournamentDetail, Long> {

	Boolean existsByBird(Bird bird);
}
