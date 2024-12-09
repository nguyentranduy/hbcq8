package com.hbc.dto.tourapply.admin;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTourApplyApproveDto implements Serializable {

	private static final long serialVersionUID = 375324540285173960L;

	private long tourId;
	private long requesterId;
	private long approverId;
	private List<TourStageApply> tourStages;
	private String memo;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TourStageApply {
		private long stageId;
		private String endPointCode;
		private String endPointCoor;
		private double endPointDist;
	}
}
