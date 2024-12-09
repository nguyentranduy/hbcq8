package com.hbc.dto.bird;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hbc.entity.Bird;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BirdResponseDto implements Serializable {

	private static final long serialVersionUID = 4545187668816501224L;

	private long id;
	private String code;
	private String name;
	private String description;
	private long userId;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;

	public static BirdResponseDto build(Bird bird) {
		BirdResponseDto dto = new BirdResponseDto();
		dto.id = bird.getId();
		dto.code = bird.getCode();
		dto.name = bird.getName();
		dto.description = bird.getDescription();
		dto.userId = bird.getUser().getId();
		dto.imgUrl = bird.getImgUrl();
		dto.createdAt = bird.getCreatedAt();
		dto.createdBy = bird.getCreatedBy();
		dto.updatedAt = bird.getUpdatedAt();
		dto.updatedBy = bird.getUpdatedBy();
		return dto;
	}
}
