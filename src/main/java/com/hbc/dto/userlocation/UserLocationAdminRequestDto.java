package com.hbc.dto.userlocation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationAdminRequestDto implements Serializable {

	private static final long serialVersionUID = -579473455293192683L;

	private long userId;
	private String code;
	private String pointCoor;
}
