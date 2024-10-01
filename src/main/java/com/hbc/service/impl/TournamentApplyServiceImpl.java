package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.hbc.constant.SessionConst;
import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyInfoDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.TournamentApply;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;
import com.hbc.exception.tourapply.TourApplyNotFoundException;
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
		
		if (ObjectUtils.isEmpty(request.getBirdCode())) {
			throw new TourApplyException("400", "Phải chọn chim đua.");
		}

		if (!tourRepo.existsById(request.getTourId())) {
			throw new TourApplyException("400", "Giải đua không tồn tại.");
		}

		request.getBirdCode().forEach(birdCode -> {
			if (!birdRepo.existsByCode(birdCode)) {
				throw new TourApplyException("400", "Chim đua không hợp lệ, mã kiềng: " + birdCode);
			}
			
			if (tournamentApplyRepo.existsByBirdCodeAndTourId(birdCode, request.getTourId())) {
				throw new TourApplyException("400", "Chim đã được đăng ký, mã kiềng: " + birdCode);
			}
		});
		
		try {
			long tourId = request.getTourId();
			long requesterId = request.getRequesterId();
			Timestamp createdAt = new Timestamp(System.currentTimeMillis());

			request.getBirdCode().forEach(birdCode -> {
				tournamentApplyRepo.doRegister(birdCode, tourId, requesterId, createdAt, requesterId);
			});
			
			List<TournamentApply> responseEntities = tournamentApplyRepo.findByTourIdAndRequesterIdAndBirdCodeIn(tourId,
					requesterId, request.getBirdCode());
			
			if (request.getBirdCode().size() != responseEntities.size()) {
				throw new IllegalStateException();
			}

			List<String> birdCodesInserted = responseEntities.stream()
					.map(i -> i.getBird().getCode()).toList();
			
			return new TourApplyResponseDto(responseEntities.get(0).getId(), birdCodesInserted,
					responseEntities.get(0).getTour().getId(), responseEntities.get(0).getTour().getName(),
					responseEntities.get(0).getTour().getStartDate(), responseEntities.get(0).getTour().getEndDate(),
					responseEntities.get(0).getRequesterId(), responseEntities.get(0).getCreatedBy(),
					responseEntities.get(0).getCreatedAt());
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Override
	public List<AdminTourApplyInfoDto> findByTourId(long tourId) throws Exception {
		List<Object[]> tourApplyRawData = tournamentApplyRepo.findCustomByTourId(tourId);

		if (ObjectUtils.isEmpty(tourApplyRawData)) {
			throw new TourApplyNotFoundException("404", "Giải đua không tồn tại.");
		}

		try {
			List<AdminTourApplyInfoDto> result = new ArrayList<>();
			
			tourApplyRawData.forEach(item -> {
				long dtoTourId = (long) item[0];
				String birdCodesRaw = (String) item[1];
				List<String> birdCodes = Arrays.asList(birdCodesRaw.split(","));
				long requesterId = (long) item[2];
				String requesterName = userRepo.findUserNameById(requesterId);
				Long approverId = null;
				String approverName = null;
				if (!ObjectUtils.isEmpty(item[3])) {
					approverId = (long) item[3];
					approverName = userRepo.findUserNameById(approverId);
				}
				boolean isBirdApplied = (boolean) item[4];
				String memo = (String) item[5];
				Timestamp createdAt = (Timestamp) item[6];
				int birdsNum = tourRepo.findBirdsNumById(dtoTourId);
				
				AdminTourApplyInfoDto dto = new AdminTourApplyInfoDto(dtoTourId, birdCodes, requesterId, requesterName,
						approverId, approverName, isBirdApplied, memo, createdAt, birdsNum);
				result.add(dto);
			});
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
