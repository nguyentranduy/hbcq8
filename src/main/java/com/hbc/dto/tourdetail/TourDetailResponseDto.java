package com.hbc.dto.tourdetail;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailResponseDto implements Serializable {

	private static final long serialVersionUID = 1924135729044709707L;

	private long tourId;
	private String tourName;
	private List<TourStageDetail> tourStages;
	private List<String> birdCodes;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TourStageDetail {
		private long stageId;
		private int orderNo;
		private String endPointCode;
		private String description;
		@JsonProperty("isActived")
		private boolean isActived;
	}
}
