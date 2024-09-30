package com.hbc.dto.tourapply;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hbc.entity.TournamentApply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourApplyResponseDto implements Serializable {

	private static final long serialVersionUID = -5672348066783882970L;

	private long id;
	private String birdCode;
	private long tourId;
	private String tourName;
	private Timestamp tourStartDate;
	private Timestamp tourEndDate;
	private long requesterId;
	private Timestamp createdAt;
	private long createdBy;
	
	public static TourApplyResponseDto build(TournamentApply entity) {
		TourApplyResponseDto dto = new TourApplyResponseDto();
		dto.setId(entity.getId());
		dto.setBirdCode(entity.getBird().getCode());
		dto.setTourId(entity.getTour().getId());
		dto.setTourName(entity.getTour().getName());
		dto.setTourStartDate(entity.getTour().getStartDate());
		dto.setTourEndDate(entity.getTour().getEndDate());
		dto.setRequesterId(entity.getRequesterId());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setCreatedBy(entity.getCreatedBy());
		
		return dto;
	}
}
