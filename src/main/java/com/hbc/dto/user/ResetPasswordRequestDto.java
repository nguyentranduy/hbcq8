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
public class ResetPasswordRequestDto implements Serializable {
	
	private static final long serialVersionUID = -2316721372404116705L;
	private String password;
}
