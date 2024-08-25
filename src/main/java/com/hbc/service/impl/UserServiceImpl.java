package com.hbc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hbc.dto.UserResponseDto;
import com.hbc.entity.User;
import com.hbc.exception.login.AuthenticationException;
import com.hbc.repo.UserRepo;
import com.hbc.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo repo;
	
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
}
