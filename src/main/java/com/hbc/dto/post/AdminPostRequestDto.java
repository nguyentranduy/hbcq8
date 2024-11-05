package com.hbc.dto.post;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPostRequestDto implements Serializable {

	private static final long serialVersionUID = -4791283349155262777L;
	
	private long categoryId;
	private String title;
	private String content;
	private String slug;
	private String imgUrl;
}
