package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hbc.entity.ContactInfo;

public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {

	@Modifying
	@Query(value = "UPDATE contact_info SET address = :address, phone1 = :phone1, name1 = :name1,"
			+ " phone2 = :phone2, name2 = :name2, email = :email"
			+ " WHERE id = 1", nativeQuery = true)
	void doUpdate(@Param("address") String address, @Param("phone1") String phone1,
			@Param("name1") String name1, @Param("phone2") String phone2,
			@Param("name2") String name2, @Param("email") String email);
}
