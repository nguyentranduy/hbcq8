package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
