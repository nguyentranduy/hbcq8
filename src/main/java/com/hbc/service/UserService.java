package com.hbc.service;

import com.hbc.dto.UserRegisterRequestDto;
import com.hbc.dto.UserResponseDto;
import com.hbc.exception.login.AuthenticationException;

public interface UserService {

	UserResponseDto doLogin(String username, String password) throws AuthenticationException;

	UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto) throws Exception;
	
	Boolean doUpdateImg(String imgUrl,String username) throws Exception;
}
