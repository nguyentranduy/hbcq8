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
	private boolean hasSubmit1;
	private String point2Code;
	private Timestamp point2Time;
	private String point2Key;
	private boolean hasSubmit2;
	private String point3Code;
	private Timestamp point3Time;
	private String point3Key;
	private boolean hasSubmit3;
	private String point4Code;
	private Timestamp point4Time;
	private String point4Key;
	private boolean hasSubmit4;
	private String point5Code;
	private Timestamp point5Time;
	private String point5Key;
	private boolean hasSubmit5;
	private String endPointCode;
	private Timestamp endPointTime;
	private String endPointKey;
	private boolean hasSubmit0;

	public static TourDetailResponseDto build(TournamentDetail entity) {
		TourDetailResponseDto dto = new TourDetailResponseDto();
		dto.tourId = entity.getTour().getId();
		dto.birdCode = entity.getBird().getCode();
		dto.startPointCode = entity.getStartPointCode();
		dto.startPointTime = entity.getStartPointTime();
		dto.point1Code = entity.getPoint1Code();
		dto.point1Time = entity.getPoint1Time();
		dto.point1Key = entity.getPoint1Key();
		dto.hasSubmit1 = entity.getPoint1Key() != null;
		dto.point2Code = entity.getPoint2Code();
		dto.point2Time = entity.getPoint2Time();
		dto.point2Key = entity.getPoint2Key();
		dto.hasSubmit2 = entity.getPoint2Key() != null;
		dto.point3Code = entity.getPoint3Code();
		dto.point3Time = entity.getPoint3Time();
		dto.point3Key = entity.getPoint3Key();
		dto.hasSubmit3 = entity.getPoint3Key() != null;
		dto.point4Code = entity.getPoint4Code();
		dto.point4Time = entity.getPoint4Time();
		dto.point4Key = entity.getPoint4Key();
		dto.hasSubmit4 = entity.getPoint4Key() != null;
		dto.point5Code = entity.getPoint5Code();
		dto.point5Time = entity.getPoint5Time();
		dto.point5Key = entity.getPoint5Key();
		dto.hasSubmit5 = entity.getPoint5Key() != null;
		dto.endPointCode = entity.getEndPointCode();
		dto.endPointTime = entity.getEndPointTime();
		dto.endPointKey = entity.getEndPointKey();
		dto.hasSubmit0 = entity.getEndPointKey() != null;
		return dto;
	}
}
