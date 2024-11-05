package com.hbc.service.impl;

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
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.Tournament;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tournament.CannotDeleteTourActivedException;
import com.hbc.exception.tournament.TourInfoFailedException;
import com.hbc.exception.tournament.TourNotFoundException;
import com.hbc.repo.TournamentRepo;
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
	UserLocationRepo userLocationRepo;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<TourResponseDto> findAllAvailable() {
		List<Tournament> tournaments = tourRepo.findByIsDeletedOrderByCreatedAtDesc(false);
		List<TourResponseDto> response = new ArrayList<>();
		tournaments.forEach(tour -> response.add(TourResponseDto.build(tour)));
		return response;
	}

	@Override
	public TourResponseDto findById(long tourId) throws TourNotFoundException {
		Optional<Tournament> tournament = tourRepo.findById(tourId);
		if (tournament.isEmpty()) {
			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
		}
		return TourResponseDto.build(tournament.get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doRegister(TourRequestDto dto, UserResponseDto currentUser) {
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}

		validateDto(dto);
		
		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getStartPointCode(), ADMIN_ID, false)) {
			throw new TourInfoFailedException("400", "Mã căn cứ bắt đầu không phù hợp.");
		}

		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getEndPointCode(), ADMIN_ID, false)) {
			throw new TourInfoFailedException("400", "Mã căn cứ kết thúc không phù hợp.");
		}

		if (tourRepo.existsByNameAndIsDeleted(dto.getName(), false)) {
			throw new TourInfoFailedException("400", "Tên giải đua đã tồn tại.");
		}

		String startPointCoor = userLocationRepo.findPointCoorByCode(dto.getStartPointCode());
		String endPointCoor = userLocationRepo.findPointCoorByCode(dto.getEndPointCode());
		Tournament tourEntity = Tournament.build(dto, startPointCoor, endPointCoor, currentUser.getId());

		try {
			Tournament tourResponse = tourRepo.save(tourEntity);
			return TourResponseDto.build(tourResponse);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TourResponseDto doUpdate(long tourId, TourRequestDto dto, UserResponseDto currentUser) {
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}
		
		if (!tourRepo.existsByIdAndIsDeleted(tourId, false)) {
			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
		}

		validateDto(dto);

		if (tourRepo.existsByNameAndIdNotAndIsDeleted(dto.getName(), tourId, false)) {
			throw new TourInfoFailedException("400", "Tên giải đua đã tồn tại.");
		}

		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getStartPointCode(), ADMIN_ID, false)) {
			throw new TourInfoFailedException("400", "Mã căn cứ bắt đầu không phù hợp.");
		}

		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getEndPointCode(), ADMIN_ID, false)) {
			throw new TourInfoFailedException("400", "Mã căn cứ kết thúc không phù hợp.");
		}

		String startPointCoor = userLocationRepo.findPointCoorByCode(dto.getStartPointCode());
		String endPointCoor = userLocationRepo.findPointCoorByCode(dto.getEndPointCode());
		Tournament tourEntity = Tournament.buildForUpdate(tourId, dto, startPointCoor, endPointCoor, currentUser.getId());

		try {
			int tourUpdatedCount = tourRepo.update(tourEntity.getName(), tourEntity.getBirdsNum(), tourEntity.getStartDate(),
					tourEntity.getEndDate(), tourEntity.getRestTimePerDay(),
					tourEntity.getStartPointCode(), tourEntity.getStartPointCoor(),
					tourEntity.getEndPointCode(), tourEntity.getEndPointCoor(),
					tourEntity.getIsActived(), tourEntity.getUpdatedAt(), tourEntity.getUpdatedBy(), tourEntity.getId());
			if (tourUpdatedCount < 1) {
				throw new IllegalStateException();
			}
			entityManager.clear();
			Tournament tourEntityUpdated = tourRepo.findById(tourEntity.getId()).get();
			return TourResponseDto.build(tourEntityUpdated);
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doDelete(long id, UserResponseDto currentUser) throws Exception {
		if (currentUser == null || currentUser.getRoleId() != RoleConst.ROLE_ADMIN) {
			throw new AuthenticationException("401", "Người dùng không có quyền này.");
		}
		
		if (!tourRepo.existsByIdAndIsDeleted(id, false)) {
			throw new TourNotFoundException("404", "Giải đua không tồn tại.");
		}
		
		if (tourRepo.existsByIdAndIsActived(id, true)) {
			throw new CannotDeleteTourActivedException("400", "Không thể xóa giải đua đang mở.");
		}

		try {
			int countDeleted = tourRepo.deleteLogical(true, true, id);
			if (countDeleted < 1) {
				throw new Exception();
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void validateDto(TourRequestDto dto) {
		if (!StringUtils.hasText(dto.getName())) {
			throw new TourInfoFailedException("400", "Tên giải đua không được bỏ trống.");
		}
		
		if (!DateValidator.isValidPeriod(dto.getStartDate(), dto.getEndDate())) {
			throw new TourInfoFailedException("400", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
		}
		
		if ((ObjectUtils.isEmpty(dto.getStartDate()) || ObjectUtils.isEmpty(dto.getEndDate())) 
				&& dto.getIsActived()) {
			throw new TourInfoFailedException("400", "Không thể bắt đầu giải đua khi chưa chọn ngày bắt đầu & ngày kết thúc.");
		}
		
		if (ObjectUtils.isEmpty(dto.getStartPointCode())) {
			throw new TourInfoFailedException("400", "Điểm bắt đầu không được bỏ trống.");
		}
		
		if (ObjectUtils.isEmpty(dto.getEndPointCode())) {
			throw new TourInfoFailedException("400", "Điểm kết thúc không được bỏ trống");
		}
	}
	
	
//	private TournamentLocation convertToTourLocationEntity(TourLocationDto tourLocationDto, Tournament tour, UserResponseDto currentUser) {
//		TournamentLocation entity = new TournamentLocation();
//		
//		entity.setStartPointCode(tourLocationDto.getStartPoint().getCode());
//		entity.setStartPointCoor(tourLocationDto.getStartPoint().getCoor());
//		
//		entity.setEndPointCode(tourLocationDto.getEndPoint().getCode());
//		entity.setEndPointCoor(tourLocationDto.getEndPoint().getCoor());
//		entity.setEndPointDist(tourLocationDto.getEndPoint().getDist());
//		
//		entity.setTour(tour);
//		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//		entity.setCreatedBy(currentUser.getId());
//		
//		boolean existsPoint1 = false;
//		boolean existsPoint2 = false;
//		boolean existsPoint3 = false;
//		boolean existsPoint4 = false;
//		
//		if (tourLocationDto.getPoint1() != null) {
//			entity.setPoint1Code(tourLocationDto.getPoint1().getCode());
//			entity.setPoint1Coor(tourLocationDto.getPoint1().getCoor());
//			entity.setPoint1Dist(tourLocationDto.getPoint1().getDist());
//			existsPoint1 = true;
//		} else {
//			entity.setPoint1Code(null);
//			entity.setPoint1Coor(null);
//			entity.setPoint1Dist(0F);
//		}
//		
//		if (existsPoint1 && tourLocationDto.getPoint2() != null) {
//			entity.setPoint2Code(tourLocationDto.getPoint2().getCode());
//			entity.setPoint2Coor(tourLocationDto.getPoint2().getCoor());
//			entity.setPoint2Dist(tourLocationDto.getPoint2().getDist());
//			existsPoint2 = true;
//		} else {
//			entity.setPoint2Code(null);
//			entity.setPoint2Coor(null);
//			entity.setPoint2Dist(0F);
//		}
//		
//		if (existsPoint2 && tourLocationDto.getPoint3() != null) {
//			entity.setPoint3Code(tourLocationDto.getPoint3().getCode());
//			entity.setPoint3Coor(tourLocationDto.getPoint3().getCoor());
//			entity.setPoint3Dist(tourLocationDto.getPoint3().getDist());
//			existsPoint3 = true;
//		} else {
//			entity.setPoint3Code(null);
//			entity.setPoint3Coor(null);
//			entity.setPoint3Dist(0F);
//		}
//		
//		if (existsPoint3 & tourLocationDto.getPoint4() != null) {
//			entity.setPoint4Code(tourLocationDto.getPoint4().getCode());
//			entity.setPoint4Coor(tourLocationDto.getPoint4().getCoor());
//			entity.setPoint4Dist(tourLocationDto.getPoint4().getDist());
//			existsPoint4 = true;
//		} else {
//			entity.setPoint4Code(null);
//			entity.setPoint4Coor(null);
//			entity.setPoint4Dist(0F);
//		}
//		
//		if (existsPoint4 & tourLocationDto.getPoint5() != null) {
//			entity.setPoint5Code(tourLocationDto.getPoint5().getCode());
//			entity.setPoint5Coor(tourLocationDto.getPoint5().getCoor());
//			entity.setPoint5Dist(tourLocationDto.getPoint5().getDist());
//		} else {
//			entity.setPoint5Code(null);
//			entity.setPoint5Coor(null);
//			entity.setPoint5Dist(0F);
//		}
//		
//		return entity;
//	}
}
