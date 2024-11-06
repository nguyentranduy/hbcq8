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

@Table(name="tournament_detail")
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
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "bird_code", referencedColumnName = "code")
	private Bird bird;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "tour_id", referencedColumnName = "id")
	private Tournament tour;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "start_point_code")
	private String startPointCode;
	
	@Column(name = "start_point_coor")
	private String startPointCoor;
	
	@Column(name = "start_point_time")
	private Timestamp startPointTime;
	
	@Column(name = "point1_code")
	private String point1Code;
	
	@Column(name = "point1_coor")
	private String point1Coor;
	
	@Column(name = "point1_dist")
	private Float point1Dist;
	
	@Column(name = "point1_time")
	private Timestamp point1Time;
	
	@Column(name = "point1_speed")
	private Float point1Speed;
	
	@Column(name = "point1_key")
	private String point1Key;
	
	@Column(name = "point2_code")
	private String point2Code;
	
	@Column(name = "point2_coor")
	private String point2Coor;
	
	@Column(name = "point2_dist")
	private Float point2Dist;
	
	@Column(name = "point2_time")
	private Timestamp point2Time;
	
	@Column(name = "point2_speed")
	private Float point2Speed;

	@Column(name = "point2_key")
	private String point2Key;
	
	@Column(name = "point3_code")
	private String point3Code;
	
	@Column(name = "point3_coor")
	private String point3Coor;
	
	@Column(name = "point3_dist")
	private Float point3Dist;
	
	@Column(name = "point3_time")
	private Timestamp point3Time;
	
	@Column(name = "point3_speed")
	private Float point3Speed;

	@Column(name = "point3_key")
	private String point3Key;
	
	@Column(name = "point4_code")
	private String point4Code;
	
	@Column(name = "point4_coor")
	private String point4Coor;
	
	@Column(name = "point4_dist")
	private Float point4Dist;
	
	@Column(name = "point4_time")
	private Timestamp point4Time;
	
	@Column(name = "point4_speed")
	private Float point4Speed;

	@Column(name = "point4_key")
	private String point4Key;
	
	@Column(name = "point5_code")
	private String point5Code;
	
	@Column(name = "point5_coor")
	private String point5Coor;
	
	@Column(name = "point5_dist")
	private Float point5Dist;
	
	@Column(name = "point5_time")
	private Timestamp point5Time;
	
	@Column(name = "point5_speed")
	private Float point5Speed;

	@Column(name = "point5_key")
	private String point5Key;
	
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
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "avg_speed")
	private Float avgSpeed;
	
	@Column(name = "rank_of_bird")
	private Long rankOfBird;
	
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
