package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourSubmitTimeRequestDto implements Serializable {

	private static final long serialVersionUID = -5714515509671183607L;

	private long tourId;
	private long requesterId;
	private int pointNo;
	private String birdCode;
	private String pointCode;
	private String pointKey;
}
