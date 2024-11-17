package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hbc.constant.SessionConst;
import com.hbc.constant.TourApplyStatusCodeConst;
import com.hbc.dto.tourapply.TourApplyRequestDto;
import com.hbc.dto.tourapply.TourApplyResponseDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyApproveDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyInfoDto;
import com.hbc.dto.tourapply.admin.AdminTourApplyRejectDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.entity.TournamentApply;
import com.hbc.entity.TournamentStage;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.tourapply.TourApplyException;
import com.hbc.exception.tourapply.TourApplyNotFoundException;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.exception.userlocation.LocationNotFoundException;
import com.hbc.repo.BirdRepo;
import com.hbc.repo.TournamentApplyRepo;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.repo.TournamentRepo;
import com.hbc.repo.TournamentStageRepo;
import com.hbc.repo.UserLocationRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.TournamentApplyService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Service
public class TournamentApplyServiceImpl implements TournamentApplyService {

	@Autowired
	TournamentApplyRepo tourApplyRepo;

	@Autowired
	TournamentDetailRepo tourDetailRepo;

	@Autowired
	BirdRepo birdRepo;

	@Autowired
	TournamentRepo tourRepo;
	
	@Autowired
	TournamentStageRepo tourStageRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserLocationRepo userLocationRepo;

	@PersistenceContext
    private EntityManager entityManager;

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
			
			if (tourApplyRepo.existsByBirdCodeAndTourId(birdCode, request.getTourId())) {
				throw new TourApplyException("400", "Chim đã được đăng ký, mã kiềng: " + birdCode);
			}
		});
		
		try {
			long tourId = request.getTourId();
			long requesterId = request.getRequesterId();
			Timestamp createdAt = new Timestamp(System.currentTimeMillis());

			request.getBirdCode().forEach(birdCode -> {
				tourApplyRepo.doRegister(birdCode, tourId, requesterId, createdAt,
						requesterId, TourApplyStatusCodeConst.STATUS_CODE_WAITING);
			});
			
			entityManager.clear();
			
			List<TournamentApply> responseEntities = tourApplyRepo.findByTourIdAndRequesterIdAndBirdCodeIn(tourId,
					requesterId, request.getBirdCode());
			
			if (request.getBirdCode().size() != responseEntities.size()) {
				throw new IllegalStateException();
			}

			List<String> birdCodesInserted = responseEntities.stream()
					.map(i -> i.getBird().getCode()).toList();
			
			return new TourApplyResponseDto(responseEntities.get(0).getId(), birdCodesInserted,
					responseEntities.get(0).getTour().getId(), responseEntities.get(0).getTour().getName(),
					responseEntities.get(0).getTour().getStartDateInfo(), responseEntities.get(0).getTour().getEndDateInfo(),
					responseEntities.get(0).getRequesterId(), responseEntities.get(0).getCreatedBy(),
					responseEntities.get(0).getCreatedAt());
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Override
	public List<AdminTourApplyInfoDto> findByTourId(long tourId) throws Exception {
		if (!tourRepo.existsById(tourId)) {
			throw new TourApplyNotFoundException("404", "Giải đua không tồn tại.");
		}
		List<Object[]> tourApplyRawData = tourApplyRepo.findCustomByTourId(tourId);

		if (ObjectUtils.isEmpty(tourApplyRawData)) {
			return List.of();
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
				String statusCode = String.valueOf(item[4]);
				String memo = (String) item[5];
				Timestamp createdAt = (Timestamp) item[6];
				int birdsNum = tourRepo.findBirdsNumById(dtoTourId);

				List<TournamentStage> tourStages = tourStageRepo.findByTourId(dtoTourId);
				
				AdminTourApplyInfoDto dto = AdminTourApplyInfoDto.build(dtoTourId, birdCodes, requesterId, requesterName,
						approverId, approverName, statusCode, memo, createdAt, birdsNum, tourStages);
				result.add(dto);
			});
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doApprove(AdminTourApplyApproveDto dto) throws Exception {
		if (!tourApplyRepo.existsByTourIdAndRequesterId(dto.getTourId(), dto.getRequesterId())) {
			throw new TourApplyNotFoundException("404", "Dữ liệu đăng ký không tồn tại.");
		}

		if (!userRepo.existsByIdAndIsDeleted(dto.getRequesterId(), false)) {
			throw new UserNotFoundException("404", "Người dùng không tồn tại.");
		}

		if (!userLocationRepo.existsByCodeAndUserIdAndIsDeleted(dto.getEndPointCode(), dto.getRequesterId(), false)) {
			throw new LocationNotFoundException("404", "Căn cứ đích không tồn tại.");
		}

		try {
			tourApplyRepo.doUpdate(TourApplyStatusCodeConst.STATUS_CODE_APPROVED, dto.getMemo(), dto.getApproverId(),
					dto.getApproverId(), new Timestamp(System.currentTimeMillis()), dto.getTourId(), dto.getRequesterId());
			doRegisterTourDetail(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doReject(AdminTourApplyRejectDto dto) throws Exception {
		if (!tourApplyRepo.existsByTourIdAndRequesterId(dto.getTourId(), dto.getRequesterId())) {
			throw new TourApplyNotFoundException("404", "Dữ liệu đăng ký không tồn tại.");
		}

		try {
			tourApplyRepo.doUpdate(TourApplyStatusCodeConst.STATUS_CODE_REJECTED, dto.getMemo(), dto.getApproverId(),
					dto.getApproverId(), new Timestamp(System.currentTimeMillis()), dto.getTourId(), dto.getRequesterId());

			tourDetailRepo.doDeleteByTourIdAndUserId(dto.getTourId(), dto.getRequesterId());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void doRegisterTourDetail(AdminTourApplyApproveDto dto) {
//		long requesterId = dto.getRequesterId();
//		long tourId = dto.getTourId();
//		long approverId = dto.getApproverId();
//		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//		
//		List<String> birdCodeList = tourApplyRepo.findBirdCodeByTourIdAndRequesterId(tourId, requesterId);
//		for (String birdCode : birdCodeList) {
//			tourDetailRepo.doRegister(requesterId, birdCode, tourId, dto.getStartPointCode(), dto.getStartPointCoor(),
//					dto.getPoint1Code(), dto.getPoint1Coor(), dto.getPoint1Dist(),
//					dto.getPoint2Code(), dto.getPoint2Coor(), dto.getPoint2Dist(),
//					dto.getPoint3Code(), dto.getPoint3Coor(), dto.getPoint3Dist(),
//					dto.getPoint4Code(), dto.getPoint4Coor(), dto.getPoint4Dist(),
//					dto.getPoint5Code(), dto.getPoint5Coor(), dto.getPoint5Dist(),
//					dto.getEndPointCode(), dto.getEndPointCoor(), dto.getEndPointDist(),
//					createdAt, approverId);
//		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doCancel(long tourId, long requesterId) throws Exception {
		int tournamentApplyCount = tourApplyRepo.countByTourIdAndRequesterId(tourId, requesterId);
		
		if (tournamentApplyCount < 1) {
			throw new TourApplyNotFoundException("404", "Đơn không tồn tại.");
		}

		String tourStatusCode = tourApplyRepo.findStatusCodeByTourIdAndRequesterId(tourId, requesterId);
		
		if (StringUtils.hasText(tourStatusCode) && !tourStatusCode.equals(TourApplyStatusCodeConst.STATUS_CODE_WAITING)) {
			throw new TourApplyException("400", "Đơn đã được phê duyệt hoặc từ chối thì không được phép hủy.");
		}
		
		try {
			tourApplyRepo.deleteByTourIdAndRequesterId(tourId, requesterId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public List<AdminTourApplyInfoDto> findByTourIdAndRequesterId(long tourId, long requesterId) throws Exception {
//		if (!tourRepo.existsById(tourId)) {
//			throw new TourApplyNotFoundException("404", "Giải đua không tồn tại.");
//		}
//		
//		List<Object[]> tourApplyRawData = tourApplyRepo.findCustomByTourIdAndRequesterId(tourId, requesterId);
//
//		if (ObjectUtils.isEmpty(tourApplyRawData)) {
//			return List.of();
//		}
//
//		try {
//			List<AdminTourApplyInfoDto> result = new ArrayList<>();
//			
//			tourApplyRawData.forEach(item -> {
//				long dtoTourId = (long) item[0];
//				String birdCodesRaw = (String) item[1];
//				List<String> birdCodes = Arrays.asList(birdCodesRaw.split(","));
//				String requesterName = userRepo.findUserNameById(requesterId);
//				Long approverId = null;
//				String approverName = null;
//				if (!ObjectUtils.isEmpty(item[3])) {
//					approverId = (long) item[3];
//					approverName = userRepo.findUserNameById(approverId);
//				}
//				String statusCode = String.valueOf(item[4]);
//				String memo = (String) item[5];
//				Timestamp createdAt = (Timestamp) item[6];
//				int birdsNum = tourRepo.findBirdsNumById(dtoTourId);
//				
//				AdminTourApplyInfoDto dto = new AdminTourApplyInfoDto(dtoTourId, birdCodes, requesterId, requesterName,
//						approverId, approverName, statusCode, memo, createdAt, birdsNum);
//				result.add(dto);
//			});
//			return result;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
//		}
		return null;
	}
}
