package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTourCancelDto implements Serializable {

	private static final long serialVersionUID = 3945441420875570886L;
	private long tourId;
	private long stageId;
	private String birdCode;
}
