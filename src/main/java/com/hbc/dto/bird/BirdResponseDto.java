package com.hbc.dto.bird;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hbc.entity.Bird;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirdResponseDto implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4545187668816501224L;

	private Long id;
	private String birdSecretKey;
	private String name;
	private Long userId;
	private String imgUrl;
	private Timestamp createdAt;
	private Long createdBy;
	private Timestamp updatedAt;
	private Long updatedBy;

	public static BirdResponseDto build(Bird bird) {
		BirdResponseDto dto = new BirdResponseDto();
		dto.id = bird.getId();
		dto.birdSecretKey = bird.getBirdSecretKey();
		dto.name = bird.getName();
		dto.userId = bird.getUser().getId();
		dto.imgUrl = bird.getImgUrl();
		dto.createdAt = bird.getCreatedAt();
		dto.createdBy = bird.getCreatedBy();
		dto.createdAt = bird.getUpdatedAt() != null ? bird.getUpdatedAt() : null;
		dto.createdBy = bird.getUpdatedBy() != null ? bird.getUser().getId() : null;
		return dto;
	}
}
