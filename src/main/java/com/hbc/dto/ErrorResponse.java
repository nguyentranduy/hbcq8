package com.hbc.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = -5685164118920946104L;

	private String errorCode;
    private String errorMessage;
}
