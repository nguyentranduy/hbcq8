package com.hbc.dto.bird;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirdUpdateRequestDto extends BirdRequestDto {

	private static final long serialVersionUID = 218429215555673533L;
	private long id;
}
