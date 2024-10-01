package com.hbc.dto.user;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hbc.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto implements Serializable {

	private static final long serialVersionUID = 4060064274583422429L;

	private long id;
	private String username;
	private String email;
	private String phone;
	private String address;
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
	private Date birthday;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;
	private int roleId;

	/**
	 * Build UserLoginResponseDto from User entity.
	 * 
	 * @param user User entity.
	 * @return a response dto.
	 */
	public static UserResponseDto build(User user) {
		UserResponseDto dto = new UserResponseDto();
		dto.id = user.getId();
		dto.username = user.getUsername();
		dto.email = user.getEmail();
		dto.phone = user.getPhone();
		dto.address = user.getAddress();
		dto.birthday = user.getBirthday();	
		dto.imgUrl = user.getImgUrl();
		dto.createdAt = user.getCreatedAt();
		dto.createdBy = user.getCreatedBy();
		dto.updatedAt = user.getUpdatedAt() != null ? user.getUpdatedAt() : null;
		dto.updatedBy = user.getUpdatedBy() != null ? user.getUpdatedBy() : null;
		dto.roleId = user.getRole().getId();
		return dto;
	}
}
