package com.hbc.dto.bird.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminBirdUpdateRequestDto implements Serializable {

	private static final long serialVersionUID = -1592404358082602921L;

	private long id;
	private String code;
	private String description;
	private String imgUrl;
	private long userId;
}
