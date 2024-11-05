package com.hbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hbc.dto.post.PostResponseDto;
import com.hbc.entity.Post;
import com.hbc.entity.User;
import com.hbc.exception.post.PostNotFoundException;
import com.hbc.repo.PostRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public PostResponseDto findBySlug(String slug) {
		Post post = postRepo.findBySlugAndIsDeleted(slug, false);
		if (ObjectUtils.isEmpty(post)) {
			throw new PostNotFoundException("404", "Bài viết không tồn tại.");
		}

		User author = userRepo.findById(post.getCreatedBy()).get();
		return PostResponseDto.build(post, author.getUsername());
	}
}
