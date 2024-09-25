package com.hbc.dto.tournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTourRequestDto extends TourRequestDto {

	private static final long serialVersionUID = -3821113955321900106L;

	private Long id;
}
