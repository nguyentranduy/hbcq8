package com.hbc.dto.bird;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirdUpdateRequestDto implements Serializable {

	private static final long serialVersionUID = 218429215555673533L;
	private long id;
	private String name;
	private String description;
	private String imgUrl;
	private long userId;
}
