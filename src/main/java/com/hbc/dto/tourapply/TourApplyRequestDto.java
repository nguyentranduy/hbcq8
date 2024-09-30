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
public class TourApplyRequestDto implements Serializable {

	private static final long serialVersionUID = -5672348066783882970L;
	
	private List<String> birdCode;
	private long tourId;
	private long requesterId;
	private Timestamp createdAt;
	private long createdBy;
}
