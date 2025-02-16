package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.AboutUs;

@Repository
public interface AboutUsRepo extends JpaRepository<AboutUs, Long> {

	@Modifying
	@Query(value = "INSERT INTO about_us(content, person1, role1, img1, person2, role2, img2,"
			+ " person3, role3, img3, person4, role4, img4, person5, role5, img5,"
			+ " person6, role6, img6)"
			+ " VALUE (:content, :person1, :role1, :img1, :person2, :role2, :img2,"
			+ " :person3, :role3, :img3, :person4, :role4, :img4,"
			+ " :person5, :role5, :img5, :person6, :role6, :img6)", nativeQuery = true)
	void insert(@Param("content") String content, @Param("person1") String person1, @Param("role1") String role1, @Param("img1") String img1,
			 @Param("person2") String person2, @Param("role2") String role2, @Param("img2") String img2,
			 @Param("person3") String person3, @Param("role3") String role3, @Param("img3") String img3,
			 @Param("person4") String person4, @Param("role4") String role4, @Param("img4") String img4,
			 @Param("person5") String person5, @Param("role5") String role5, @Param("img5") String img5,
			 @Param("person6") String person6, @Param("role6") String role6, @Param("img6") String img6);
}
