package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Tournament;

@Repository
public interface TournamentRepo extends JpaRepository<Tournament, Long> {

}
