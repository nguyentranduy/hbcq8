package com.hbc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hbc.entity.User;
import com.hbc.repo.UserRepo;
import com.hbc.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo repo;
	
	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Override
	public User doLogin(String username, String password) {
		Optional<User> userResponseOptional = repo.findByUsername(username);

		if (userResponseOptional.isEmpty()) {
			return null;
		}

		User userResponse = userResponseOptional.get();
		
		return bcrypt.matches(password, userResponse.getPassword()) ? userResponse : null;
	}
}
