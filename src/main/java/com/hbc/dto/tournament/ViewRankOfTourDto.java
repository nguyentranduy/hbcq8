package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRankOfTourDto implements Serializable {

	private static final long serialVersionUID = -6657771447846053227L;

	private long rank;
	private String userLocationCode;
	private String userLocationName;
	private String birdCode;
	private String userLocationCoor;
	private double distance;
	private double speed;
}
