package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.constant.RoleConst;
import com.hbc.dto.tournament.TourRequestDto;
import com.hbc.dto.tournament.TourResponseDto;
import com.hbc.dto.tourstage.TourStageRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentStage;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.exception.tournament.TourNotFoundException;
import com.hbc.repo.TournamentRepo;
import com.hbc.repo.TournamentStageRepo;
import com.hbc.repo.UserLocationRepo;
import com.hbc.service.TournamentService;
import com.hbc.validator.DateValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TournamentServiceImpl implements TournamentService {

	private static final int ADMIN_ID = 1;

	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	TournamentStageRepo tourStageRepo;
	
	@Autowired
	UserLocationRepo userLocationRepo;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<TourResponseDto> findAllAvailable() {
		List<Tournament> tournaments = tourRepo.findByIsDeletedOrderByCreatedAtDesc(false);
		List<TourResponseDto> response = new ArrayList<>();
		tournaments.forEach(tour -> response.add(TourResponseDto.build(tour, List.of())));
		return response;
	}

	@Override
	public TourResponseDto findById(long tourId) throws TourNotFoundException {
		Optional<Tournament> tournament = tourRepo.findById(tourId);
		if (tournament.isEmpty()) {
			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
		}
		
		List<TournamentStage> tourStageRaw = tourStageRepo.findByTourId(tourId);
		return TourResponseDto.build(tournament.get(), tourStageRaw);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser) {
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}

		validateDto(dto);
		if (tourRepo.existsByNameAndIsDeleted(dto.getName(), false)) {
			throw new TourInfoFailedException("400", "Tên giải đua đã tồn tại.");
		}

		try {
			Tournament tourResponse = tourRepo.saveAndFlush(Tournament.build(dto, currentUser.getId()));
			dto.getTourStages().forEach(tourStage -> {
				tourStageRepo.insert(tourResponse.getId(), tourStage.getOrderNo(), tourStage.getRestTimePerDay(),
						tourStage.getStartPointCode(), tourStage.getStartPointName(), tourStage.getStartPointCoor(),
						new Timestamp(System.currentTimeMillis()), currentUser.getId());
			});
			List<TournamentStage> tourStageInserted = tourStageRepo.findByTourId(tourResponse.getId());
			return TourResponseDto.build(tourResponse, tourStageInserted);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doUpdate(long tourId, TourRequestDto dto, UserResponseDto currentUser) {
//		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
//			throw new AuthenticationException("401", "Người dùng không có quyền này.");
//		}
//		
//		if (!tourRepo.existsByIdAndIsDeleted(tourId, false)) {
//			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
//		}
//
//		validateDto(dto);
//
//		if (tourRepo.existsByNameAndIdNotAndIsDeleted(dto.getName(), tourId, false)) {
//			throw new TourInfoFailedException("400", "Tên giải đua đã tồn tại.");
//		}
//
//		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getStartPointCode(), ADMIN_ID, false)) {
//			throw new TourInfoFailedException("400", "Mã căn cứ bắt đầu không phù hợp.");
//		}
//
//		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getEndPointCode(), ADMIN_ID, false)) {
//			throw new TourInfoFailedException("400", "Mã căn cứ kết thúc không phù hợp.");
//		}
//
//		String startPointCoor = userLocationRepo.findPointCoorByCode(dto.getStartPointCode());
//		String endPointCoor = userLocationRepo.findPointCoorByCode(dto.getEndPointCode());
//		Tournament tourEntity = Tournament.buildForUpdate(tourId, dto, startPointCoor, endPointCoor, currentUser.getId());
//
//		try {
//			int tourUpdatedCount = tourRepo.update(tourEntity.getName(), tourEntity.getBirdsNum(), tourEntity.getStartDate(),
//					tourEntity.getEndDate(), tourEntity.getRestTimePerDay(),
//					tourEntity.getStartPointCode(), tourEntity.getStartPointCoor(), tourEntity.getStartPointTime(),
//					tourEntity.getEndPointCode(), tourEntity.getEndPointCoor(),
//					tourEntity.getIsActived(), tourEntity.getUpdatedAt(), tourEntity.getUpdatedBy(), tourEntity.getId());
//			if (tourUpdatedCount < 1) {
//				throw new IllegalStateException();
//			}
//			entityManager.clear();
//			Tournament tourEntityUpdated = tourRepo.findById(tourEntity.getId()).get();
//			return TourResponseDto.build(tourEntityUpdated);
//		} catch (Exception ex) {
//			throw new CustomException("400", ex.getMessage());
//		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doDelete(long id, UserResponseDto currentUser) throws Exception {
//		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
//			throw new AuthenticationException("401", "Người dùng không có quyền này.");
//		}
//		
//		if (!tourRepo.existsByIdAndIsDeleted(id, false)) {
//			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
//		}
//		
//		if (tourRepo.existsByIdAndIsActived(id, true)) {
//			throw new CannotDeleteTourActivedException("400", "Không thể xóa giải đua đang mở.");
//		}
//
//		try {
//			int countDeleted = tourRepo.deleteLogical(true, false, id);
//			if (countDeleted < 1) {
//				throw new Exception();
//			}
//		} catch (Exception ex) {
//			throw ex;
//		}
	}

	private void validateDto(TourRequestDto dto) {
		if (!StringUtils.hasText(dto.getName())) {
			throw new TourInfoFailedException("400", "Tên giải đua không được bỏ trống.");
		}
		
		if (!DateValidator.isValidPeriod(dto.getStartDateReceive(), dto.getEndDateReceive())) {
			throw new TourInfoFailedException("400", "Ngày bắt đầu nhận đơn phải nhỏ hơn ngày kết thúc.");
		}
		
		List<TourStageRequestDto> tourStages = dto.getTourStages();
		if (ObjectUtils.isEmpty(tourStages)) {
			throw new TourInfoFailedException("400", "Chặng đua không được bỏ trống.");
		}
	}
}
