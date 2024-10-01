package com.hbc.dto.tourlocation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalDistanceRequestDto implements Serializable {

	private static final long serialVersionUID = -1244878908648711268L;
	
	private String startPoint;
	private String point1;
	private String point2;
	private String point3;
	private String point4;
	private String point5;
	private String endPoint;
}
