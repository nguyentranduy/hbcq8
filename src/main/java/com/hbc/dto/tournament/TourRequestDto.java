package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourRequestDto implements Serializable {

	private static final long serialVersionUID = -5201269028402956941L;
	private String name;
	private Integer birdsNum;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startDate;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDate;	
	private Float restTimePerDay;
	private String startPointCode;
	private String endPointCode;
	private Boolean isActived;
	private TourLocationDto tourLocation;
}
