package com.hbc.dto.tourapply.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.hbc.entity.TournamentStage;

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
	private List<TourStageInfo> tourStages;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class TourStageInfo {
		private long stageId;
		private int orderNo;
		private String startPointCode;
		private String startPointCoor;
		private String startPointName;
	}
	
	public static AdminTourApplyInfoDto build (long tourId, List<String> birdCodes, long requesterId,
			String requesterName, Long approverId, String approverName, String statusCode, String memo,
			Timestamp createdAt, int birdsNum, List<TournamentStage> tourStagesRaw) {
		AdminTourApplyInfoDto result = new AdminTourApplyInfoDto();
		result.tourId = tourId;
		result.birdCodes = birdCodes;
		result.requesterId = requesterId;
		result.requesterName = requesterName;
		result.approverId = approverId;
		result.approverName = approverName;
		result.statusCode = statusCode;
		result.memo = memo;
		result.createdAt = createdAt;
		result.birdsNum = birdsNum;
		
		List<TourStageInfo> tourStagesInfo = new ArrayList<>();
		tourStagesRaw.forEach(i -> {
			TourStageInfo stageInfo = new TourStageInfo(i.getId(), i.getOrderNo(), i.getStartPointCode(),
					i.getStartPointCoor(), i.getStartPointName());
			tourStagesInfo.add(stageInfo);
		});
		
		result.tourStages = tourStagesInfo;
		return result;
	}
}
