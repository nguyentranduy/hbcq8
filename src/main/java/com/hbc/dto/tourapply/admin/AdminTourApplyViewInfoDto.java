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
public class AdminTourApplyViewInfoDto implements Serializable {

	private static final long serialVersionUID = -2833635055819734276L;

	private long tourId;
	private List<String> birdCodes;
	private long requesterId;
	private String requesterName;
	private List<TourStageInfo> tourStages;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class TourStageInfo {
		private int orderNo;
		private String startPointCode;
		private String startPointCoor;
	}
}
