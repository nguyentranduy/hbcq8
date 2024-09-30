package com.hbc.dto.user;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto implements Serializable {
	
	private static final long serialVersionUID = -4232237805613075535L;

	private Long userId;
	private String address;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date birthday;
	private String phone;
}
