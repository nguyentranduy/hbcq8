package com.hbc.dto.tourlocation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalDistanceTwoPointRequestDto implements Serializable {

	private static final long serialVersionUID = -1244878908648711268L;
	
	private String startPoint;
	private String endPoint;
}
