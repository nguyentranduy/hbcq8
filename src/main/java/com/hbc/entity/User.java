package com.hbc.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = -6150517181586432342L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String username;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String phone;
	
	@Column
	private String address;
	
	@Column
	private Date birthday;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "updated_by")
	private Long updatedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	
	public static User buildNewUser(String username, String password, String email, String phone,
			int roleId, String birthday) throws IllegalArgumentException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setRole(new Role(roleId));
		user.setCreatedBy(0L);
		user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		user.setIsDeleted(false);
		
		if (!birthday.isEmpty()) {
			try {
				user.setBirthday(Date.valueOf(birthday)); // yyyy-MM-dd
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("Birthday must be follow the pattern: yyyy-MM-dd");
			}
		}
		
		return user;
	}
}
