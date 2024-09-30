package com.hbc.dto.tourapply;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourApplyRequestDto implements Serializable {

	private static final long serialVersionUID = -5672348066783882970L;
	
	private long id;
	private String birdCode;
	private long tourId;
	private boolean isBirdApplied;
	private long requesterId;
	private long approverId;
	private String memo;
	private Timestamp createdAt;
	private long createdBy;
	private Timestamp updatedAt;
	private long updatedBy;
}
