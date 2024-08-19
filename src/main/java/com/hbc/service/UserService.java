package com.hbc.service;

import com.hbc.entity.User;

public interface UserService {

	User doLogin(String username, String password);
}
