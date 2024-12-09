package com.hbc.dto.tourapply.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTourApplyRejectDto implements Serializable {

	private static final long serialVersionUID = 375324540285173960L;

	private long tourId;
	private long requesterId;
	private Long approverId;
	private String memo;
}
