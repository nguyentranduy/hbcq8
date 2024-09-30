package com.hbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.repo.TournamentApplyRepo;
import com.hbc.service.TournamentApplyService;

@Service
public class TournamentApplyServiceImpl implements TournamentApplyService {

	@Autowired
	TournamentApplyRepo repo;
}
