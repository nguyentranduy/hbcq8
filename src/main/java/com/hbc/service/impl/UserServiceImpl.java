package com.hbc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hbc.dto.UserRegisterRequestDto;
import com.hbc.dto.UserResponseDto;
import com.hbc.entity.User;
import com.hbc.exception.login.AuthenticationException;
import com.hbc.repo.UserRepo;
import com.hbc.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo repo;
	private final String FOLDER_PATH = "C:\\hcmimg\\";

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
	public UserResponseDto doRegister(UserRegisterRequestDto userRegisterRequestDto) throws Exception {
		// TODO Auto-generated method stub
		Optional<User> userResponseOptional = repo.findByUsernameAndEmailAndPhone(userRegisterRequestDto.getUsername(),
				userRegisterRequestDto.getEmail(), userRegisterRequestDto.getPhone());

		if (userResponseOptional.isEmpty()) {

			User user = userRegisterRequestDto.doBuildUser();
			return UserResponseDto.build(repo.save(user));
		
		}

		throw new Exception();
	}

	@Override
	public Boolean doUpdateImg(String imgUrl, String username) throws Exception {	
		return repo.updateimgUrlByUsername(imgUrl, username);
	}
	
}
