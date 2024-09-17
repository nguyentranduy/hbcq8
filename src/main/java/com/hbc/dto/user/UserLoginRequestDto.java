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
public class UserLoginRequestDto implements Serializable {

	private static final long serialVersionUID = 3524646768017726731L;
	
	private String username;
	private String password;
}
