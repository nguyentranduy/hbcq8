package com.hbc.dto.bird;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBirdRequestDto implements Serializable {

	private static final long serialVersionUID = 8075065438234302050L;
	
	private String name;
	private String code;
	private String imgUrl;
	private long userId;
}
