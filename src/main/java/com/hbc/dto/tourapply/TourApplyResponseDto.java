package com.hbc.dto.tourapply;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourApplyResponseDto implements Serializable {

	private static final long serialVersionUID = -5672348066783882970L;

	private String birdCode;
	private long tourId;
	private long requesterId;
	private Timestamp createdAt;
	private long createdBy;
	private Timestamp updatedAt;
	private long updatedBy;
}
