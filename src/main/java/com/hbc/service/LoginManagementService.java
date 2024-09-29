package com.hbc.service;

import com.hbc.entity.LoginManagement;

public interface LoginManagementService {

	LoginManagement findByUserIdAndToken(long userId, String token);
	LoginManagement createToken(long userId) throws Exception;
	void deactiveToken(long userId, String token);
}
