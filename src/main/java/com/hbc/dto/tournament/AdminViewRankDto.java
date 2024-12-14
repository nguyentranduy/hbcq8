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
public class AdminViewRankDto implements Serializable {

	private static final long serialVersionUID = 2327397615677079144L;

	private long rank;
	private String userLocationCode;
	private String userLocationName;
	private String birdCode;
	private String userLocationCoor;
	private double distance;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endTime;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startTime;
	private String totalTime;
	private float speed;
	private String pointKey;
}
