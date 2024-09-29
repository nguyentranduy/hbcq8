package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hbc.dto.tournament.TourLocationDto.Point;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseDto implements Serializable {

	private static final long serialVersionUID = -8680668600270264689L;

	private Long id;
	private String name;
	private Integer birdsNum;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp startDate;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp endDate;
	private Float restTimePerDay;
	private Boolean isActived;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss.SSS")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss.SSS")
	private Timestamp updatedAt;
	private Long updatedBy;
	private TourLocationDto tourLocation;

	/**
	 * Build TourResponseDto from Tournament entity.
	 * 
	 * @param entity Tournament entity.
	 * @return a response dto.
	 */
	public static TourResponseDto build(Tournament tour, TournamentLocation tourLocation) {
		TourResponseDto dto = new TourResponseDto();
		dto.setId(tour.getId());
		dto.setName(tour.getName());
		dto.setBirdsNum(tour.getBirdsNum());
		dto.setImgUrl(tour.getImgUrl());
		dto.setStartDate(tour.getStartDate());
		dto.setEndDate(tour.getEndDate());
		dto.setRestTimePerDay(tour.getRestTimePerDay());
		dto.setIsActived(tour.getIsActived());
		dto.setCreatedAt(tour.getCreatedAt());
		dto.setCreatedBy(tour.getCreatedBy());
		dto.setUpdatedAt(tour.getUpdatedAt());
		dto.setUpdatedBy(tour.getUpdatedBy());
		dto.setTourLocation(buildTourLocationDto(tourLocation));
		
		return dto;
	}
	
	private static TourLocationDto buildTourLocationDto(TournamentLocation tourLocation) {
		TourLocationDto tourLocationDto = new TourLocationDto();
		tourLocationDto.setStartPoint(new Point(tourLocation.getStartPointName(), tourLocation.getStartPointCoor(), 0F));
		tourLocationDto.setEndPoint(new Point(tourLocation.getEndPointName(), tourLocation.getEndPointCoor(), tourLocation.getEndPointDist()));
		
		if (StringUtils.hasText(tourLocation.getPoint1Name())) {
			tourLocationDto.setPoint1(new Point(tourLocation.getPoint1Name(), tourLocation.getPoint1Coor(), tourLocation.getPoint1Dist()));
		}
		
		if (StringUtils.hasText(tourLocation.getPoint2Name())) {
			tourLocationDto.setPoint2(new Point(tourLocation.getPoint2Name(), tourLocation.getPoint2Coor(), tourLocation.getPoint2Dist()));
		}
		
		if (StringUtils.hasText(tourLocation.getPoint3Name())) {
			tourLocationDto.setPoint3(new Point(tourLocation.getPoint3Name(), tourLocation.getPoint3Coor(), tourLocation.getPoint3Dist()));
		}
		
		if (StringUtils.hasText(tourLocation.getPoint4Name())) {
			tourLocationDto.setPoint4(new Point(tourLocation.getPoint4Name(), tourLocation.getPoint4Coor(), tourLocation.getPoint4Dist()));
		}
		
		if (StringUtils.hasText(tourLocation.getPoint5Name())) {
			tourLocationDto.setPoint5(new Point(tourLocation.getPoint5Name(), tourLocation.getPoint5Coor(), tourLocation.getPoint5Dist()));
		}
		
		return tourLocationDto;
	}
}
