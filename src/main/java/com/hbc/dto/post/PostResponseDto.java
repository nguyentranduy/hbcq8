package com.hbc.dto.post;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hbc.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto implements Serializable {

	private static final long serialVersionUID = -1154214882512633534L;

	private long id;
	private long categoryId;
	private String imgUrl;
	private String slug;
	private String title;
	private String content;
	private String authorUserName;
	@JsonProperty("isDeleted")
	private boolean isDeleted;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;
	
	public static PostResponseDto build(Post post, String authorUserName) {
		PostResponseDto dto = new PostResponseDto();
		dto.id = post.getId();
		dto.categoryId = post.getCategory().getId();
		dto.imgUrl = post.getImgUrl();
		dto.slug = post.getSlug();
		dto.title = post.getTitle();
		dto.content = post.getContent();
		dto.authorUserName = authorUserName;
		dto.isDeleted = post.getIsDeleted();
		dto.createdAt = post.getCreatedAt();
		dto.createdBy = post.getCreatedBy();
		dto.updatedAt = post.getUpdatedAt();
		dto.updatedBy = post.getUpdatedBy();
		return dto;
	}
}
