package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.bird.BirdResponseDto;
import com.hbc.entity.Bird;
import com.hbc.entity.User;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.bird.DuplicatedBirdException;
import com.hbc.exception.bird.KeyConstraintBirdException;
import com.hbc.repo.BirdRepo;
import com.hbc.repo.TournamentDetailRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.BirdService;

import jakarta.transaction.Transactional;

@Service
public class BirdServiceImpl implements BirdService {
	@Autowired
	BirdRepo birdRepo;

	@Autowired
	TournamentDetailRepo tuDetailRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public Boolean doDelete(Long id) throws KeyConstraintBirdException, CustomException {
		// Kiểm tra xem Bird có tồn tại trong tuDetailRepo hay không
		if (tuDetailRepo.existsByBird(new Bird(id))) {
			throw new KeyConstraintBirdException("409", "Chim da tham gia giai dua khong the xoa");
		} else if (!birdRepo.existsById(id)) {
			throw new CustomException("404", "Bird not found");
		} else {
			// Nếu không có ràng buộc, tiến hành xóa Bird trong birdRepo
			birdRepo.deleteById(id);
			return true;
		}
	}

	@Override
	public List<BirdResponseDto> doGetBirds(Long userId) throws AuthenticationException {
		if (userRepo.existsById(userId)) {
			List<Bird> list = birdRepo.findByUser(new User(userId));
			List<BirdResponseDto> listResponseDtos = new ArrayList<>();
			for (Bird bird : list) {
				listResponseDtos.add(BirdResponseDto.build(bird));
			}
			return listResponseDtos;
		}
		throw new AuthenticationException("400", "User not found.");

	}

	@Override
	public BirdResponseDto doGetBird(String birdSecretKey) throws CustomException {
		Bird bird = birdRepo.findByBirdSecretKey(birdSecretKey);
		if (bird == null) {
			throw new CustomException("404", "Bird not found.");
		}
		return BirdResponseDto.build(bird);
	}

	@Override
	@Transactional
	public BirdResponseDto doInsert(BirdResponseDto dto) throws DuplicatedBirdException, CustomException {
		// TODO Auto-generated method stub
		if (birdRepo.existsByBirdSecretKey(dto.getBirdSecretKey())) {
			throw new DuplicatedBirdException("409", "BirdBirdSecretKey already exists");
		}
		try {
			Bird bird = Bird.buildNewBird(dto.getBirdSecretKey(), dto.getName(), dto.getUserId());
			birdRepo.save(bird);
			Bird bird2 = birdRepo.findByBirdSecretKey(dto.getBirdSecretKey());
			System.out.println("thu" + bird2);
			if (bird2 == null) {
				throw new CustomException("400", "Cannot create bird");
			}
			return dto.build(bird2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("400", e.getMessage());
		}

	}

	@Override
	@Transactional
	public BirdResponseDto doUpdate(BirdResponseDto dto) throws AuthenticationException, CustomException {
		if (!birdRepo.existsById(dto.getId())) {
			throw new AuthenticationException("401", "Bird not found");
		}
		try {
			int updateRecord = birdRepo.update(dto.getName(), dto.getUserId(),
					new Timestamp(System.currentTimeMillis()), dto.getId());
			if (updateRecord < 1) {
				throw new CustomException("400", String.format("Cannot update bird with id:{0}", dto.getId()));
			}
			Bird bird = birdRepo.findById(dto.getId()).get();
			if (bird == null) {
				throw new CustomException("400", String.format("Cannot update bird with id:{0}", dto.getId()));
			}
			BirdResponseDto birdResponseDto = BirdResponseDto.build(bird);
			return birdResponseDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("400", e.getMessage());
		}
	}

	@Override
	public Boolean doUpdateImg(String birdSecretKey, String imgUrl) throws AuthenticationException, CustomException {
		if (!birdRepo.existsByBirdSecretKey(birdSecretKey)) {
			throw new AuthenticationException("401", "Bird not found.");
		}
		try {
			int updated = birdRepo.updateimgUrlById(imgUrl, birdSecretKey);

			if (updated < 1) {
				throw new CustomException("400", String.format("Can't update avatar for bird {0}", birdSecretKey));
			}
			return true;
		} catch (Exception e) {
			throw new CustomException("400", String.format("Can't update avatar for bird {0}", e.getMessage()));
		}

	}

}
