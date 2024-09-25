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
    
	/**
	 * Build TourResponseDto from Tournament entity.
	 * 
	 * @param entity Tournament entity.
	 * @return a response dto.
	 */
    public static TourResponseDto build(Tournament entity) {
    	TourResponseDto dto = new TourResponseDto();
    	dto.setId(entity.getId());
    	dto.setName(entity.getName());
    	dto.setBirdsNum(entity.getBirdsNum());
    	dto.setImgUrl(entity.getImgUrl());
    	dto.setStartDate(entity.getStartDate());
    	dto.setEndDate(entity.getEndDate());
    	dto.setRestTimePerDay(entity.getRestTimePerDay());
    	dto.setIsActived(entity.getIsActived());
    	dto.setCreatedAt(entity.getCreatedAt());
    	dto.setCreatedBy(entity.getCreatedBy());
    	dto.setUpdatedAt(entity.getUpdatedAt());
    	dto.setUpdatedBy(entity.getUpdatedBy());
    	
    	return dto;
    }
}
