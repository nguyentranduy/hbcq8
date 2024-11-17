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

	@Column
	private String description;

	@Column(name = "birds_num")
	private Integer birdsNum;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@Column(name = "start_date_info")
	private Timestamp startDateInfo;
	
	@Column(name = "end_date_info")
	private Timestamp endDateInfo;
	
	@Column(name = "start_date_receive")
	private Timestamp startDateReceive;
	
	@Column(name = "end_date_receive")
	private Timestamp endDateReceive;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "is_finished")
	private Boolean isFinished;
	
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
		entity.setDescription(tourRequestDto.getDescription());
		entity.setBirdsNum(tourRequestDto.getBirdsNum());
		entity.setImgUrl(tourRequestDto.getImgUrl());
		entity.setStartDateInfo(tourRequestDto.getStartDateInfo());
		entity.setEndDateInfo(tourRequestDto.getEndDateInfo());
		entity.setStartDateReceive(tourRequestDto.getStartDateReceive());
		entity.setEndDateReceive(tourRequestDto.getEndDateReceive());
		entity.setCreatedBy(createdBy);
		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		entity.setIsDeleted(false);
		entity.setIsFinished(false);
		return entity;
	}
}

