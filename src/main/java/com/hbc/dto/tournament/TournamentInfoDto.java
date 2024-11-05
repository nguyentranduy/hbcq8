package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentInfoDto implements Serializable {

	private static final long serialVersionUID = 6207552318955197674L;

	private long tourId;
	private String tourName;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startDate;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDate;
	private String startLocationCode;
	private String endLocationCode;
	private int birdsNum;
	private String tourApplyStatusCode;
	@JsonProperty("isActived")
	private boolean isActived;
	private String tourStatus;
	private String memo;
}
