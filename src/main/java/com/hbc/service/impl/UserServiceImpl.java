package com.hbc.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.constant.RoleConst;
import com.hbc.constant.SessionConst;
import com.hbc.dto.user.ChangePasswordRequestDto;
import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateRequestDto;
import com.hbc.entity.User;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.CustomException;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.repo.UserRepo;
import com.hbc.service.UserService;
import com.hbc.validator.UserValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo repo;
	
    @PersistenceContext
    private EntityManager entityManager;

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Override
	public UserResponseDto doLogin(String username, String password) throws AuthenticationException {

		Optional<User> userResponseOptional = repo.findByUsernameAndIsDeleted(username, false);

		if (userResponseOptional.isEmpty()) {
			throw new AuthenticationException("401", "User account not found.");
		}

		User userResponse = userResponseOptional.get();

		if (bcrypt.matches(password, userResponse.getPassword())) {
			return UserResponseDto.build(userResponse);
		}

		throw new AuthenticationException("401", "Incorrect password. Please try again.");
	}

	@Override
	@Transactional
	public UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto)
			throws DuplicatedUserException, CustomException {
		if (repo.existsByUsername(userRegisterRequestDto.getUsername())) {
			throw new DuplicatedUserException("409", "Username already exists.");
		}
		
		if (repo.existsByEmail(userRegisterRequestDto.getEmail())) {
			throw new DuplicatedUserException("409", "Email already exists.");
		}
		Optional<User> userWithPhone = repo.findByPhone(userRegisterRequestDto.getPhone());
		
		if (userWithPhone.isPresent()) {
			throw new DuplicatedUserException("409", "Phone number already exists.");
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
	public UserResponseDto doUpdate(UserUpdateRequestDto userUpdateRequestDto, HttpSession session)
			throws DuplicatedUserException, AuthenticationException {

		if (!UserValidator.canSelfUpdated(userUpdateRequestDto.getUserId(), session)) {
			throw new AuthenticationException("401-01", "User do not have permission to update.");
		}

		if (!repo.existsByIdAndIsDeleted(userUpdateRequestDto.getUserId(), Boolean.FALSE)) {
			session.removeAttribute(SessionConst.CURRENT_USER);
			throw new AuthenticationException("401-02", "User account not found.");
		}

		Optional<User> userWithPhone = repo.findByPhoneAndIdNot(userUpdateRequestDto.getPhone(), userUpdateRequestDto.getUserId());
		
		if (userWithPhone.isPresent()) {
			throw new DuplicatedUserException("409", "Phone number already exists.");
		}

		try {
			int updatedRecord = repo.update(userUpdateRequestDto.getPhone(), userUpdateRequestDto.getAddress(),
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
	public UserResponseDto findById(long userId) {
		return UserResponseDto.build(repo.findById(userId).get());
	}

	@Override
	public List<UserResponseDto> findAllAvailable() {
		List<User> dataRaw = repo.findByIsDeletedOrderByUsernameAsc(false);
		return dataRaw.stream().map(UserResponseDto::build).toList();
	}

	@Override
	public UserResponseDto findByIdAvailable(long userId) {
		return UserResponseDto.build(repo.findByIdAndIsDeleted(userId, false).get());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void doChangePass(UserResponseDto currentUser, ChangePasswordRequestDto dto) throws Exception {
		if (!currentUser.getUsername().equals(dto.getUsername())) {
			throw new AuthenticationException("401", "Not have permission.");
		}
		
		Optional<User> currentUserOpt = repo.findByUsernameAndIsDeleted(dto.getUsername(), Boolean.FALSE);
		if (currentUserOpt.isEmpty()) {
			throw new AuthenticationException("401-02", "User account not found.");
		}
		User user = currentUserOpt.get();
		if (!bcrypt.matches(dto.getCurrentPass(), user.getPassword())) {
			throw new AuthenticationException("401", "Incorrect password. Please try again.");
		}

		String hashPassword = bcrypt.encode(dto.getNewPass());
		repo.changePassword(hashPassword, user.getId());
	}
}
