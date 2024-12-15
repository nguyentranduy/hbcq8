package com.hbc.dto.tourdetail;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTourDetailDto implements Serializable {

	private static final long serialVersionUID = 5844805847323731407L;

	private long tourId;
	private long stageId;
	private String birdCode;
	private String startPointCode;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startTime;
	private String endPointCode;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endTime;
	private String pointKey;
	private String status;
}
