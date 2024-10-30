package com.hbc.dto.tourlocation;

import com.hbc.entity.TournamentLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourLocationDto {

	private Long id;
	private Long tourId;
	private Point startPoint;
	private Point point1;
	private Point point2;
	private Point point3;
	private Point point4;
	private Point point5;
	private Point endPoint;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Point {
		private String code;
		private String coor;
		private Float dist;
	}
	
	/**
	 * Build TourLocationDto from TournamentLocation entity.
	 * 
	 * @param entity TournamentLocation entity.
	 * @return a response dto.
	 */
	public static TourLocationDto build(TournamentLocation entity) {
		TourLocationDto dto = new TourLocationDto();
		dto.id = entity.getId();
		dto.tourId = entity.getTour().getId();
		dto.startPoint = new Point(entity.getStartPointCode(), entity.getStartPointCoor(), 0F);
		dto.point1 = new Point(entity.getPoint1Code(), entity.getPoint1Coor(), entity.getPoint1Dist());
		dto.point2 = new Point(entity.getPoint2Code(), entity.getPoint2Coor(), entity.getPoint2Dist());
		dto.point3 = new Point(entity.getPoint3Code(), entity.getPoint3Coor(), entity.getPoint3Dist());
		dto.point4 = new Point(entity.getPoint4Code(), entity.getPoint4Coor(), entity.getPoint4Dist());
		dto.point5 = new Point(entity.getPoint5Code(), entity.getPoint5Coor(), entity.getPoint5Dist());
		dto.endPoint = new Point(entity.getEndPointCode(), entity.getEndPointCoor(), entity.getEndPointDist());
		
		return dto;
	}
}
