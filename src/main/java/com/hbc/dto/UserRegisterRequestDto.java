package com.hbc.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hbc.entity.Role;
import com.hbc.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto implements Serializable {

	private static final long serialVersionUID = 3239108323095064461L;

	private String username;
	private String password;
	private String email;
	private String phone;

	public User doBuildUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setRole(new Role(3, "User"));
		user.setCreatedBy(1L);
		user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		user.setIsDeleted(false);
		return user;
	}

}
