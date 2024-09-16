package com.hbc.service;

import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.UserRegisterRequestDto;
import com.hbc.dto.UserResponseDto;
import com.hbc.exception.login.AuthenticationException;
import com.hbc.exception.register.DuplicatedUserException;

public interface UserService {

	UserResponseDto doLogin(String username, String password) throws AuthenticationException;

	UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto) throws DuplicatedUserException;
	
	Boolean doUpdateImg(MultipartFile file,String username) throws Exception;
	
	UserResponseDto doUpdate(UserResponseDto userResponseDto) throws Exception;
}
