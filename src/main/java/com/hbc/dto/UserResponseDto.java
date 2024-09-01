package com.hbc.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
	private String birthday;
	private String imgUrl;
	private Timestamp createdAt;
	private long createdBy;
	private Timestamp updatedAt;
	private long updatedBy;
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

		if (!(dto.birthday == null)) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			dto.birthday = df.format(user.getBirthday());
		}

		dto.imgUrl = user.getImgUrl();
		dto.createdAt = user.getCreatedAt();

		dto.createdBy = user.getCreatedBy();
		dto.updatedAt = user.getUpdatedAt();

		if (!(user.getUpdatedBy() == null)) {
			dto.updatedBy = user.getUpdatedBy();
		}
		dto.roleId = user.getRole().getId();

		return dto;
	}
}
