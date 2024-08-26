package com.hbc.service;

import com.hbc.dto.UserResponseDto;
import com.hbc.exception.login.AuthenticationException;

public interface UserService {

	UserResponseDto doLogin(String username, String password) throws AuthenticationException;
}
