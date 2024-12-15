package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.constant.RoleConst;
import com.hbc.dto.user.ResetPasswordRequestDto;
import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateAdminRequestDto;
import com.hbc.entity.User;
import com.hbc.exception.CustomException;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.exception.user.InvalidActionException;
import com.hbc.exception.user.UserNotFoundException;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminUserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	UserRepo repo;
	
    @PersistenceContext
    private EntityManager entityManager;

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto)
			throws DuplicatedUserException, CustomException {
		if (repo.existsByUsername(userRegisterRequestDto.getUsername())) {
			throw new DuplicatedUserException("409", "Tên tài khoản đã tồn tại.");
		}
		
		if (repo.existsByEmail(userRegisterRequestDto.getEmail())) {
			throw new DuplicatedUserException("409", "Địa chỉ email đã tồn tại.");
		}
		Optional<User> userWithPhone = repo.findByPhone(userRegisterRequestDto.getPhone());
		
		if (userWithPhone.isPresent()) {
			throw new DuplicatedUserException("409", "Số điện thoại đã tồn tại.");
		}

		String hashPassword = bcrypt.encode(userRegisterRequestDto.getPassword());
		try {
			User user = User.buildNewUser(userRegisterRequestDto.getUsername(), hashPassword,
					userRegisterRequestDto.getEmail(), userRegisterRequestDto.getPhone(),
					RoleConst.ROLE_USER, userRegisterRequestDto.getBirthday());
			return UserResponseDto.build(repo.save(user));
		} catch (Exception ex) {
			throw new CustomException("400", ex.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserResponseDto doUpdate(UserUpdateAdminRequestDto userUpdateRequestDto, HttpSession session)
			throws DuplicatedUserException, UserNotFoundException {

		Optional<User> currentUser = repo.findByIdAndIsDeleted(userUpdateRequestDto.getUserId(), false);

		if (currentUser.isEmpty()) {
			throw new UserNotFoundException("404", "Người dùng không tồn tại.");
		}

		if (!currentUser.get().getUsername().equals(userUpdateRequestDto.getUsername())
				&& repo.existsByUsername(userUpdateRequestDto.getUsername())) {
			throw new DuplicatedUserException("409", "Tên tài khoản đã tồn tại.");
		}

		Optional<User> userWithPhone = repo.findByPhoneAndIdNot(userUpdateRequestDto.getPhone(), userUpdateRequestDto.getUserId());
		
		if (userWithPhone.isPresent()) {
			throw new DuplicatedUserException("409", "Số điện thoại đã tồn tại.");
		}

		try {
			int updatedRecord = repo.updateIncludeUsername(userUpdateRequestDto.getUsername(),
					userUpdateRequestDto.getPhone(), userUpdateRequestDto.getAddress(),
					userUpdateRequestDto.getBirthday(), userUpdateRequestDto.getImgUrl(),
					new Timestamp(System.currentTimeMillis()), userUpdateRequestDto.getUserId(),
					userUpdateRequestDto.getUserId());
	
			if (updatedRecord < 1) {
				throw new CustomException("400", String.format("Cannot update user with id: {0}",
						userUpdateRequestDto.getUserId()));
			}

			entityManager.clear();

			User userResponse = repo.findById(userUpdateRequestDto.getUserId()).get();
			
			if (userResponse == null) {
				throw new CustomException("400", String.format("Cannot update user with id: {0}",
						userUpdateRequestDto.getUserId()));
			}

			UserResponseDto userResponseDto = UserResponseDto.build(userResponse);
			return userResponseDto;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException("400", String.format("Cannot update user with id: {0}",
					userUpdateRequestDto.getUserId()));
		}
	}

	@Override
	public List<UserResponseDto> findAllAvailable() {
		List<User> dataRaw = repo.findByIsDeletedOrderByUsernameAsc(false);
		return dataRaw.stream().map(UserResponseDto::build).toList();
	}

	@Override
	public UserResponseDto findByIdAvailable(long userId) throws UserNotFoundException {
		Optional<User> user = repo.findByIdAndIsDeleted(userId, false);
		if (user.isPresent()) {
			return UserResponseDto.build(user.get());	
		}
		throw new UserNotFoundException("404", "Tài khoản không tồn tại.");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteById(long userId, long currentUserId) throws UserNotFoundException, InvalidActionException {
		if (userId == currentUserId) {
			throw new InvalidActionException("403", "Không thể tự xóa chính mình.");
		}
		
		if (!repo.existsByIdAndIsDeleted(userId, false)) {
			throw new UserNotFoundException("404", "Người dùng không tồn tại.");
		}

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		repo.deleteLogical(updatedAt, currentUserId, userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doResetPassword(ResetPasswordRequestDto resetPasswordRequestDto, long userId) {
		repo.updatePassword(resetPasswordRequestDto.getPassword(), userId);
	}
}
