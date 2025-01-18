package com.hbc.dto.contactinfo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactRequestDto implements Serializable {

	private static final long serialVersionUID = 3442906410776567385L;

	private String address;
	private String phone1;
	private String name1;
	private String phone2;
	private String name2;
	private String email;
}
