package com.hbc.dto.user;

import java.io.Serializable;

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
	private String birthday;
}
