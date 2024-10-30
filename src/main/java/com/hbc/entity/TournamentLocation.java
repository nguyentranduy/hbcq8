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

@Table(name="tournament_location")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentLocation implements Serializable {

	private static final long serialVersionUID = -6150517181586432342L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Column(name = "point1_code")
	private String point1Code;
	
	@Column(name = "point1_coor")
	private String point1Coor;
	
	@Column(name = "point1_dist")
	private Float point1Dist;
	
	@Column(name = "point2_code")
	private String point2Code;
	
	@Column(name = "point2_coor")
	private String point2Coor;
	
	@Column(name = "point2_dist")
	private Float point2Dist;
	
	@Column(name = "point3_code")
	private String point3Code;
	
	@Column(name = "point3_coor")
	private String point3Coor;
	
	@Column(name = "point3_dist")
	private Float point3Dist;
	
	@Column(name = "point4_code")
	private String point4Code;
	
	@Column(name = "point4_coor")
	private String point4Coor;
	
	@Column(name = "point4_dist")
	private Float point4Dist;
	
	@Column(name = "point5_code")
	private String point5Code;
	
	@Column(name = "point5_coor")
	private String point5Coor;
	
	@Column(name = "point5_dist")
	private Float point5Dist;
	
	@Column(name = "end_point_code")
	private String endPointCode;
	
	@Column(name = "end_point_coor")
	private String endPointCoor;
	
	@Column(name = "end_point_dist")
	private Float endPointDist;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	public long getTourIdValue() {
		return this.tour.getId();
	}
}
