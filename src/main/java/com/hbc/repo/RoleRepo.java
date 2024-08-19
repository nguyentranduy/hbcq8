package com.hbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
