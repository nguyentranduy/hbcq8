package com.hbc.dto.bird.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBirdRequestDto implements Serializable {

	private static final long serialVersionUID = 8075065438234302050L;
	
	private String code;
	private long userId;
}
