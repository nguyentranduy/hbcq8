package com.hbc.dto.tourapply;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourApplyResponseDto implements Serializable {

	private static final long serialVersionUID = -5672348066783882970L;

	private long id;
	private List<String> birdCodes;
	private long tourId;
	private String tourName;
	private Timestamp tourStartDate;
	private Timestamp tourEndDate;
	private long requesterId;
	private long createdBy;
	private Timestamp createdAt;
}
