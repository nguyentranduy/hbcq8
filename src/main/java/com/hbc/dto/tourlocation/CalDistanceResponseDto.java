package com.hbc.dto.tourlocation;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalDistanceResponseDto implements Serializable {

	private static final long serialVersionUID = -1244878908648711268L;
	
	private double startPoint;
	private double point1;
	private double point2;
	private double point3;
	private double point4;
	private double point5;
	private double endPoint;
}
