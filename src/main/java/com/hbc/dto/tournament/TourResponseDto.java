package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hbc.entity.Tournament;

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
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startDate;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDate;
	private Float restTimePerDay;
	private String startPointCode;
	private String startPointCoor;
	private String endPointCode;
	private String endPointCoor;
	private Boolean isActived;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;

    /**
     * Build TourResponseDto from Tournament entity.
     * 
     * @param entity Tournament entity.
     * @return a response dto.
     */
    public static TourResponseDto build(Tournament tour) {
        TourResponseDto dto = new TourResponseDto();
//        dto.setId(tour.getId());
//        dto.setName(tour.getName());
//        dto.setBirdsNum(tour.getBirdsNum());
//        dto.setImgUrl(tour.getImgUrl());
//        dto.setStartDate(tour.getStartDate());
//        dto.setEndDate(tour.getEndDate());
//        dto.setRestTimePerDay(tour.getRestTimePerDay());
//        dto.setStartPointCode(tour.getStartPointCode());
//        dto.setStartPointCoor(tour.getStartPointCoor());
//        dto.setEndPointCode(tour.getEndPointCode());
//        dto.setEndPointCoor(tour.getEndPointCoor());
//        dto.setIsActived(tour.getIsActived());
//        dto.setCreatedAt(tour.getCreatedAt());
//        dto.setCreatedBy(tour.getCreatedBy());
//        dto.setUpdatedAt(tour.getUpdatedAt());
//        dto.setUpdatedBy(tour.getUpdatedBy());
        
        return dto;
    }
}
