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
	@JoinColumn(name = "bird_id", referencedColumnName = "id")
	private Bird bird;
	
	@Column(name = "bird_secret_key")
	private String birdSecretKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "tour_id", referencedColumnName = "id")
	private Tournament tour;
	
	@Column(name = "start_point_time")
	private Timestamp startPointTime;
	
	@Column(name = "point1_time")
	private Timestamp point1Time;
	
	@Column(name = "point1_speed")
	private Float point1Speed;
	
	@Column(name = "point2_time")
	private Timestamp point2Time;
	
	@Column(name = "point2_speed")
	private Float point2Speed;
	
	@Column(name = "point3_time")
	private Timestamp point3Time;
	
	@Column(name = "point3_speed")
	private Float point3Speed;
	
	@Column(name = "point4_time")
	private Timestamp point4Time;
	
	@Column(name = "point4_speed")
	private Float point4Speed;
	
	@Column(name = "point5_time")
	private Timestamp point5Time;
	
	@Column(name = "point5_speed")
	private Float point5Speed;
	
	@Column(name = "end_point_time")
	private Timestamp endPointTime;
	
	@Column(name = "end_point_speed")
	private Float endPointSpeed;
	
	@Column(name = "avg_speed")
	private Float avgSpeed;
	
	@Column(name = "rank_of_bird")
	private Long rankOfBird;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private Long updatedBy;
}
