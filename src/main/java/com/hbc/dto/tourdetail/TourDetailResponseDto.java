package com.hbc.dto.tourdetail;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hbc.entity.TournamentDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailResponseDto implements Serializable {

	private static final long serialVersionUID = 1924135729044709707L;

	private long tourId;
	private String birdCode;
	private String startPointCode;
	private Timestamp startPointTime;
	private String point1Code;
	private Timestamp point1Time;
	private String point1Key;
	private String point2Code;
	private Timestamp point2Time;
	private String point2Key;
	private String point3Code;
	private Timestamp point3Time;
	private String point3Key;
	private String point4Code;
	private Timestamp point4Time;
	private String point4Key;
	private String point5Code;
	private Timestamp point5Time;
	private String point5Key;
	private String endPointCode;
	private Timestamp endPointTime;
	private String endPointKey;

	public static TourDetailResponseDto build(TournamentDetail entity) {
		TourDetailResponseDto dto = new TourDetailResponseDto();
		dto.tourId = entity.getTour().getId();
		dto.birdCode = entity.getBird().getCode();
		dto.startPointCode = entity.getStartPointCode();
		dto.startPointTime = entity.getStartPointTime();
		dto.point1Code = entity.getPoint1Code();
		dto.point1Time = entity.getPoint1Time();
		dto.point1Key = entity.getPoint1Key();
		dto.point2Code = entity.getPoint2Code();
		dto.point2Time = entity.getPoint2Time();
		dto.point2Key = entity.getPoint2Key();
		dto.point3Code = entity.getPoint3Code();
		dto.point3Time = entity.getPoint3Time();
		dto.point3Key = entity.getPoint3Key();
		dto.point4Code = entity.getPoint4Code();
		dto.point4Time = entity.getPoint4Time();
		dto.point4Key = entity.getPoint4Key();
		dto.point5Code = entity.getPoint5Code();
		dto.point5Time = entity.getPoint5Time();
		dto.point5Key = entity.getPoint5Key();
		dto.endPointCode = entity.getEndPointCode();
		dto.endPointTime = entity.getEndPointTime();
		dto.endPointKey = entity.getEndPointKey();
		return dto;
	}
}
