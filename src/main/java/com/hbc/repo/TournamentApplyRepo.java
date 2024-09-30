package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.TournamentApply;

@Repository
public interface TournamentApplyRepo extends JpaRepository<TournamentApply, Long> {

}
