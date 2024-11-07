package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRankDto implements Serializable {

	private static final long serialVersionUID = 2327397615677079144L;

	private long rank;
	private String birdCode;
	private float avgSpeed;
}
