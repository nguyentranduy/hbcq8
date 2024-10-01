package com.hbc.dto.tournament;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourLocationDto {
	private Point startPoint;
	private Point point1;
	private Point point2;
	private Point point3;
	private Point point4;
	private Point point5;
	private Point endPoint;
	private Timestamp createdAt;
	private Long createdBy;
	private Timestamp updatedAt;
	private Long updatedBy;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Point {
		private String name;
		private String coor;
		private Float dist;
	}
}
