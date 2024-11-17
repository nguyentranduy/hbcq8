package com.hbc.dto.tourstage;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourStageRequestDto implements Serializable {

	private static final long serialVersionUID = -1469443880846296728L;

	private int orderNo;
	private float restTimePerDay;
	private String startPointCode;
	private String startPointName;
	private String startPointCoor;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startTime;
}
