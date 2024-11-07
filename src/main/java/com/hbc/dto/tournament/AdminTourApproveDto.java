package com.hbc.dto.tournament;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTourApproveDto implements Serializable {

	private static final long serialVersionUID = -8022873554056512634L;

	private long tourId;
	private String birdCode;
}
