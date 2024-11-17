package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private Timestamp startDateInfo;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDateInfo;
	private int birdsNum;
	private String tourApplyStatusCode;
	private String memo;
}
