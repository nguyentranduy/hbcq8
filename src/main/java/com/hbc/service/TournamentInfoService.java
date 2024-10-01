package com.hbc.service;

import java.util.List;

import com.hbc.dto.tournament.TournamentInfoDto;

public interface TournamentInfoService {

	List<TournamentInfoDto> doGetList(long requesterId);
}
