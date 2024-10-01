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

@Table(name="tournament_apply")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentApply implements Serializable {

	private static final long serialVersionUID = 5256799611817190738L;

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
	
	@Column(name = "status_code")
	private String statusCode;
	
	@Column(name = "approver_id")
	private Long approverId;
	
	@Column(name = "requester_id")
	private Long requesterId;
	
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
