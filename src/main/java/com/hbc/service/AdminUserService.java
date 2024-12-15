package com.hbc.service;

import java.util.List;

import com.hbc.dto.user.ResetPasswordRequestDto;
import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateAdminRequestDto;
import com.hbc.exception.register.DuplicatedUserException;
import com.hbc.exception.user.InvalidActionException;
import com.hbc.exception.user.UserNotFoundException;

import jakarta.servlet.http.HttpSession;

public interface AdminUserService {
	
	UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto) throws DuplicatedUserException;

	UserResponseDto doUpdate(UserUpdateAdminRequestDto userUpdateRequestDto, HttpSession session)
			throws DuplicatedUserException, UserNotFoundException;
	
	void doResetPassword(ResetPasswordRequestDto resetPasswordRequestDto, long userId);

	UserResponseDto findByIdAvailable(long userId) throws UserNotFoundException;

	List<UserResponseDto> findAllAvailable();
	
	void deleteById(long userId, long currentUserId) throws UserNotFoundException, InvalidActionException;
}
