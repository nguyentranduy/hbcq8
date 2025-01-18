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
public class ChangePasswordRequestDto implements Serializable {

	private static final long serialVersionUID = -156869442426456244L;
	private String username;
	private String currentPass;
	private String newPass;
}
