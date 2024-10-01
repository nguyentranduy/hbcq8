package com.hbc.dto.tourapply.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTourApplyInfoDto implements Serializable {

	private static final long serialVersionUID = 375324540285173960L;

	private long tourId;
	private List<String> birdCodes;
	private long requesterId;
	private String requesterName;
	private Long approverId;
	private String approverName;
	private String statusCode;
	private String memo;
	private Timestamp createdAt;
	private int birdsNum;
}
