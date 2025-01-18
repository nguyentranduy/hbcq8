package com.hbc.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="contact_info")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo implements Serializable {

	private static final long serialVersionUID = 5251005213093545576L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String address;
	
	@Column
	private String phone1;
	
	@Column
	private String name1;
	
	@Column
	private String phone2;
	
	@Column
	private String name2;
	
	@Column
	private String email;
}
