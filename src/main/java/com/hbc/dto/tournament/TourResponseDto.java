package com.hbc.dto.tournament;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hbc.entity.Tournament;
import com.hbc.entity.TournamentStage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseDto implements Serializable {

	private static final long serialVersionUID = -8680668600270264689L;

	private Long id;
	private String name;
	private String description;
	private Integer birdsNum;
	private String imgUrl;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startDateInfo;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDateInfo;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp startDateReceive;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp endDateReceive;
	private Boolean isDeleted;
	private Boolean isFinished;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp createdAt;
	private Long createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS", timezone = "Asia/Ho_Chi_Minh")
	private Timestamp updatedAt;
	private Long updatedBy;
	private List<TourStageResponse> tourStages;
	private int totalBirds;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private static class TourStageResponse {
		private long stageId;
		private int orderNo;
		private String description;
		private float restTimePerDay;
		private String startPointCode;
		private String startPointName;
		private String startPointCoor;
		@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
		private Timestamp startTime;
		private boolean isActived;
	}
	
    /**
     * Build TourResponseDto from Tournament entity.
     * 
     * @param entity Tournament entity.
     * @return a response dto.
     */
    public static TourResponseDto build(Tournament tour, List<TournamentStage> tourStagesRaw, int totalBirds) {
        TourResponseDto dto = new TourResponseDto();
        dto.setId(tour.getId());
        dto.setName(tour.getName());
        dto.setDescription(tour.getDescription());
        dto.setBirdsNum(tour.getBirdsNum());
        dto.setImgUrl(tour.getImgUrl());
        dto.setStartDateInfo(tour.getStartDateInfo());
        dto.setEndDateInfo(tour.getEndDateInfo());
        dto.setStartDateReceive(tour.getStartDateReceive());
        dto.setEndDateReceive(tour.getEndDateReceive());
        dto.setIsDeleted(tour.getIsDeleted());
        dto.setIsFinished(tour.getIsFinished());
        dto.setCreatedAt(tour.getCreatedAt());
        dto.setCreatedBy(tour.getCreatedBy());
        dto.setUpdatedAt(tour.getUpdatedAt());
        dto.setUpdatedBy(tour.getUpdatedBy());
        dto.setTotalBirds(totalBirds);
        
		LinkedHashMap<Integer, TournamentStage> sortedtourStages = tourStagesRaw.stream()
				.sorted(Comparator.comparingInt(TournamentStage::getOrderNo))
				.collect(Collectors.toMap(TournamentStage::getOrderNo, stage -> stage,
						(existing, replacement) -> existing, LinkedHashMap::new));

		List<TourStageResponse> tourStagesOrdered = new ArrayList<>();

		sortedtourStages.forEach((key, value) -> {
			TourStageResponse response = new TourStageResponse(value.getId(), value.getOrderNo(), value.getDescription(),
					value.getRestTimePerDay(), value.getStartPointCode(), value.getStartPointName(),
					value.getStartPointCoor(), value.getStartTime(), value.getIsActived());
			tourStagesOrdered.add(response);
		});
		dto.setTourStages(tourStagesOrdered);
		return dto;
    }
}
