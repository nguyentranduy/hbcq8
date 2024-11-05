package com.hbc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hbc.dto.tournament.TourRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="tournament")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tournament implements Serializable {

	private static final long serialVersionUID = -6150517181586432342L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column(name = "birds_num")
	private Integer birdsNum;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@Column(name = "start_date")
	private Timestamp startDate;
	
	@Column(name = "end_date")
	private Timestamp endDate;
	
	@Column(name = "rest_time_per_day")
	private Float restTimePerDay;
	
	@Column(name = "is_actived")
	private Boolean isActived;
	
	@Column(name = "start_point_code")
	private String startPointCode;
	
	@Column(name = "start_point_coor")
	private String startPointCoor;
	
	@Column(name = "end_point_code")
	private String endPointCode;
	
	@Column(name = "end_point_coor")
	private String endPointCoor;

	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	public static Tournament build(TourRequestDto tourRequestDto, String startPointCoor, String endPointCoor, long createdBy) {
		Tournament entity = new Tournament();
		entity.setName(tourRequestDto.getName());
		entity.setBirdsNum(tourRequestDto.getBirdsNum());
		entity.setImgUrl(tourRequestDto.getImgUrl());
		entity.setStartDate(tourRequestDto.getStartDate());
		entity.setEndDate(tourRequestDto.getEndDate());
		entity.setStartPointCode(tourRequestDto.getStartPointCode());
		entity.setStartPointCoor(startPointCoor);
		entity.setEndPointCode(tourRequestDto.getEndPointCode());
		entity.setEndPointCoor(endPointCoor);
		entity.setRestTimePerDay(tourRequestDto.getRestTimePerDay());
		entity.setIsActived(tourRequestDto.getIsActived());
		entity.setCreatedBy(createdBy);
		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		entity.setIsDeleted(false);
		
		return entity;
	}
	
	public static Tournament buildForUpdate(long tourId, TourRequestDto tourRequestDto,
			String startPointCoor, String endPointCoor, long updatedBy) {
		Tournament entity = new Tournament();
		entity.setId(tourId);
		entity.setName(tourRequestDto.getName());
		entity.setBirdsNum(tourRequestDto.getBirdsNum());
		entity.setImgUrl(tourRequestDto.getImgUrl());
		entity.setStartDate(tourRequestDto.getStartDate());
		entity.setEndDate(tourRequestDto.getEndDate());
		entity.setRestTimePerDay(tourRequestDto.getRestTimePerDay());
		entity.setStartPointCode(tourRequestDto.getStartPointCode());
		entity.setStartPointCoor(startPointCoor);
		entity.setEndPointCode(tourRequestDto.getEndPointCode());
		entity.setEndPointCoor(endPointCoor);
		entity.setIsActived(tourRequestDto.getIsActived());
		entity.setUpdatedBy(updatedBy);
		entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		return entity;
	}
}

