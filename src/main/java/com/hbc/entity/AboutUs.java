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

@Table(name = "about_us")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AboutUs implements Serializable {

	private static final long serialVersionUID = -2109635731813658801L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@Column
	private String person1;
	@Column
	private String role1;
	@Column
	private String img1;

	@Column
	private String person2;
	@Column
	private String role2;
	@Column
	private String img2;

	@Column
	private String person3;
	@Column
	private String role3;
	@Column
	private String img3;

	@Column
	private String person4;
	@Column
	private String role4;
	@Column
	private String img4;

	@Column
	private String person5;
	@Column
	private String role5;
	@Column
	private String img5;

	@Column
	private String person6;
	@Column
	private String role6;
	@Column
	private String img6;
}
