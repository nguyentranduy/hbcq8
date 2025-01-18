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
public class AdminPostRequestUpdateDto implements Serializable {

	private static final long serialVersionUID = 8708022842874153207L;

	private long id;
	private long categoryId;
	private String title;
	private String content;
	private String imgUrl;
}
