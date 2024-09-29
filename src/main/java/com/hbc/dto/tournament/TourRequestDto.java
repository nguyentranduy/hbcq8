package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TourRequestDto implements Serializable {

	private static final long serialVersionUID = -5201269028402956941L;
	private String name;
	private Integer birdsNum;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp startDate;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp endDate;	
	private Float restTimePerDay;
	private Boolean isActived;
	private TourLocationDto tourLocation;
}
