package com.hbc.dto.pdf;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PdfInputDto implements Serializable {

	private static final long serialVersionUID = 3529494952642062788L;

	private long tourId;
	private String pointCode;
	private String birdCode;
	private String pointKey;
	private String pointSubmitTime;
}
