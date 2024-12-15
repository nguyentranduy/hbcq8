package com.hbc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="tournament_stage")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentStage implements Serializable {

	private static final long serialVersionUID = 9026782047543648042L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "tour_id", referencedColumnName = "id")
	private Tournament tour;

	@Column(name = "order_no")
	private Integer orderNo;

	@Column
	private String description;

	@Column(name = "rest_time_per_day")
	private Float restTimePerDay;

	@Column(name = "start_point_code")
	private String startPointCode;

	@Column(name = "start_point_name")
	private String startPointName;

	@Column(name = "start_point_coor")
	private String startPointCoor;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "is_actived")
	private Boolean isActived;

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
}

