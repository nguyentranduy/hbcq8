package com.hbc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.hbc.entity.LoginManagement;
import com.hbc.entity.User;
import com.hbc.repo.LoginManagementRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.LoginManagementService;
import com.hbc.util.RandomStringUtil;

@Service
public class LoginManagementServiceImpl implements LoginManagementService {

	@Autowired
	LoginManagementRepo repo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public LoginManagement findByUserIdAndToken(long userId, String token) {
		return repo.findByUserIdAndTokenAndIsActived(userId, token, true);
	}

	@Override
	public LoginManagement createToken(long userId) throws Exception {
		String token = RandomStringUtil.generateRandomString();
		LoginManagement existsLogin = findByUserIdAndToken(userId, token);
		if (!ObjectUtils.isEmpty(existsLogin)) {
			throw new Exception();
		}
		
		Optional<User> user = userRepo.findByIdAndIsDeleted(userId, false);
		
		if (user.isEmpty()) {
			throw new Exception();
		}
		
		LoginManagement loginManagement = new LoginManagement();
		loginManagement.setUser(user.get());
		loginManagement.setIsActived(true);
		loginManagement.setToken(token);
		
		return repo.save(loginManagement);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deactiveToken(long userId, String token) {
		repo.deactivedToken(userId, token);
	}
}
