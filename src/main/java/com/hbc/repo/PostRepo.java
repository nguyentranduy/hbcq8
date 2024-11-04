package com.hbc.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByIsDeleted(boolean isDeleted);
}
