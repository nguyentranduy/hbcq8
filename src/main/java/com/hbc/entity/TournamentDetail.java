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

@Table(name = "tournament_detail")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDetail implements Serializable {

	private static final long serialVersionUID = -6150517181586432342L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "bird_code", referencedColumnName = "code")
	private Bird bird;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "tour_id", referencedColumnName = "id")
	private Tournament tour;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "stage_id", referencedColumnName = "id")
	private TournamentStage tourStage;

	@Column(name = "end_point_code")
	private String endPointCode;

	@Column(name = "end_point_coor")
	private String endPointCoor;

	@Column(name = "end_point_dist")
	private Float endPointDist;

	@Column(name = "end_point_time")
	private Timestamp endPointTime;

	@Column(name = "end_point_speed")
	private Float endPointSpeed;

	@Column(name = "end_point_key")
	private String endPointKey;

	@Column(name = "end_point_submit_time")
	private Timestamp endPointSubmitTime;

	@Column(name = "status")
	private String status;

	@Column(name = "memo")
	private String memo;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
}
