package com.hbc.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.constant.SessionConst;
import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.TournamentApply;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;
import com.hbc.repo.BirdRepo;
import com.hbc.repo.TournamentApplyRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.TournamentApplyService;

import jakarta.servlet.http.HttpSession;

@Service
public class TournamentApplyServiceImpl implements TournamentApplyService {

	@Autowired
	TournamentApplyRepo tournamentApplyRepo;
	
	@Autowired
	BirdRepo birdRepo;
	
	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	UserRepo userRepo;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TourApplyResponseDto doRegister(TourApplyRequestDto request, HttpSession session)
			throws AuthenticationException, TourApplyException, CustomException {
		UserResponseDto currentUser = (UserResponseDto) session.getAttribute(SessionConst.CURRENT_USER);

		if (currentUser == null || currentUser.getId() != request.getRequesterId()) {
			throw new AuthenticationException("401", "Không có quyền thao tác.");
		}

		if (!userRepo.existsByIdAndIsDeleted(request.getRequesterId(), false)) {
			session.removeAttribute(SessionConst.CURRENT_USER);
			throw new AuthenticationException("401", "Không có quyền thao tác.");
		}

		if (!tourRepo.existsById(request.getTourId())) {
			throw new TourApplyException("400", "Giải đua không tồn tại.");
		}

		if (!birdRepo.existsByCode(request.getBirdCode())) {
			throw new TourApplyException("400", "Chim đua không hợp lệ, mã kiềng: " + request.getBirdCode());
		}

		if (tournamentApplyRepo.existsByBirdCodeAndTourId(request.getBirdCode(), request.getTourId())) {
			throw new TourApplyException("400", "Chim đã được đăng ký, mã kiềng: " + request.getBirdCode());
		}
		
		try {
			tournamentApplyRepo.doRegister(request.getBirdCode(), request.getTourId(), request.getRequesterId(),
					new Timestamp(System.currentTimeMillis()), request.getRequesterId());
			TournamentApply responseEntity = tournamentApplyRepo.findByBirdCodeAndTourIdAndRequesterId(request.getBirdCode(),
					request.getTourId(), request.getRequesterId());
			
			return TourApplyResponseDto.build(responseEntity);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}
}
