package com.hbc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.user.UserRegisterRequestDto;
import com.hbc.dto.user.UserResponseDto;
import com.hbc.dto.user.UserUpdateRequestDto;
import com.hbc.exception.AuthenticationException;
import com.hbc.exception.register.DuplicatedUserException;

import jakarta.servlet.http.HttpSession;

public interface UserService {

	UserResponseDto doLogin(String username, String password) throws AuthenticationException;

	UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto) throws DuplicatedUserException;
	
	Boolean doUpdateImg(MultipartFile file,String username) throws Exception;
	
	UserResponseDto doUpdate(UserUpdateRequestDto userUpdateRequestDto, HttpSession session)
			throws DuplicatedUserException, AuthenticationException;
	
	UserResponseDto findById(long userId);
	
	List<UserResponseDto> findAllAvailable();
}
