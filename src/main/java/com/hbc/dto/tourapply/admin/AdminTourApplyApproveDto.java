package com.hbc.dto.tourapply.admin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTourApplyApproveDto implements Serializable {

	private static final long serialVersionUID = 375324540285173960L;

	private long tourId;
	private long requesterId;
	private Long approverId;
	private String startPointCode;
	private String startPointCoor;
	private String point1Code;
	private String point1Coor;
	private double point1Dist;
	private String point2Code;
	private String point2Coor;
	private double point2Dist;
	private String point3Code;
	private String point3Coor;
	private double point3Dist;
	private String point4Code;
	private String point4Coor;
	private double point4Dist;
	private String point5Code;
	private String point5Coor;
	private double point5Dist;
	private String endPointCode;
	private String endPointCoor;
	private double endPointDist;
	private String memo;
}
