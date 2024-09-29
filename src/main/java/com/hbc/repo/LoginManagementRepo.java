package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.LoginManagement;

@Repository
public interface LoginManagementRepo extends JpaRepository<LoginManagement, Long> {

	LoginManagement findByUserIdAndTokenAndIsActived(long userId, String token, boolean isActived);
	
	@Modifying
	@Query(value = "UPDATE login_management SET is_actived = 0 WHERE user_id = :userId AND token = :token", nativeQuery = true)
	void deactivedToken(@Param("userId") long userId, @Param("token") String token);
}
