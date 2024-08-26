package com.hbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);
}
