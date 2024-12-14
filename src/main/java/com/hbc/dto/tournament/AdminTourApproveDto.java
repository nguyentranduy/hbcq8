package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTourApproveDto implements Serializable {

	private static final long serialVersionUID = -4474677550099304848L;
	private long tourId;
	private long stageId;
	private String birdCode;
}
