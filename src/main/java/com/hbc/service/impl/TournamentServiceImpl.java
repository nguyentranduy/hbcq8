package com.hbc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.constant.RoleConst;
import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.repo.TournamentRepo;
import com.hbc.service.TournamentService;
import com.hbc.validator.DateValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
	
	@Autowired
	TournamentRepo repo;

	@Override
	public List<TourResponseDto> findAllAvailable() {
		List<Tournament> tournaments = repo.findByIsActived(true);
		return tournaments.stream().map(TourResponseDto::build).collect(Collectors.toList());
	}

	@Override
	public Tournament findByIdAvailable(long tourId) {
		return repo.findByIdAndIsActived(tourId, true);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser) {
		log.info("User {} started create new tournament", currentUser.getUsername());
		
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}
		
		validateDto(dto);
		
		if (repo.existsByName(dto.getName())) {
			throw new TourInfoFailedException("409-11", "Tên giải đua đã tồn tại.");
		}
		
		try {
			Tournament entity = Tournament.build(dto, currentUser.getId(), 0);
			return TourResponseDto.build(repo.save(entity));
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}
	
	private void validateDto(TourRequestDto dto) {
		if (!StringUtils.hasText(dto.getName())) {
			throw new CustomException("400", "Tên giải đua không được bỏ trống.");
		}
		
		if (!DateValidator.isValidPeriod(dto.getStartDate(), dto.getEndDate())) {
			throw new CustomException("400", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
		}
		
		if ((ObjectUtils.isEmpty(dto.getStartDate()) || ObjectUtils.isEmpty(dto.getEndDate())) 
				&& dto.getIsActived()) {
			throw new CustomException("400", "Không thể bắt đầu giải đua khi chưa chọn ngày bắt đầu & ngày kết thúc.");
		}
	}
}
