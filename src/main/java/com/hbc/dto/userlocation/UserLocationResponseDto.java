package com.hbc.dto.userlocation;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hbc.entity.UserLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationResponseDto implements Serializable {

	private static final long serialVersionUID = -1757814822282112940L;

	private long id;
	private String code;
	private String name;
	private String pointCoor;
	@JsonProperty("isDeleted")
	private boolean isDeleted;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;

	public static UserLocationResponseDto build(UserLocation entity) {
		UserLocationResponseDto dto = new UserLocationResponseDto();
		dto.id = entity.getId();
		dto.code = entity.getCode();
		dto.name = entity.getName();
		dto.pointCoor = entity.getPointCoor();
		dto.isDeleted = entity.getIsDeleted();
		dto.createdAt = entity.getCreatedAt();
		dto.createdBy = entity.getCreatedBy();
		dto.updatedAt = entity.getUpdatedAt();
		dto.updatedBy = entity.getUpdatedBy();
		return dto;
	}
}
