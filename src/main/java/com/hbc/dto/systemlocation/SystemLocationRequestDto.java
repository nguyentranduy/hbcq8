package com.hbc.dto.systemlocation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemLocationRequestDto implements Serializable {

	private static final long serialVersionUID = -1757814822282112940L;

	private String code;
	private String name;
	private String pointCoor;
}
