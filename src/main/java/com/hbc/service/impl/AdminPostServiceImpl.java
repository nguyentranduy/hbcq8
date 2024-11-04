package com.hbc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbc.dto.post.AdminPostResponseDto;
import com.hbc.entity.Post;
import com.hbc.entity.User;
import com.hbc.repo.PostRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminPostService;

@Service
public class AdminPostServiceImpl implements AdminPostService {

	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public List<AdminPostResponseDto> findAllAvailable() {
		List<Post> posts = postRepo.findByIsDeleted(false);
		List<Long> authorIds = posts.stream().map(Post::getCreatedBy).toList();
		List<User> users = userRepo.findByIdIn(authorIds);
		Map<Long, String> mapAuthors = users.stream().collect(Collectors.toMap(User::getId, User::getUsername));

		List<AdminPostResponseDto> result = new ArrayList<>();
		posts.forEach(i -> {
			result.add(AdminPostResponseDto.build(i, mapAuthors.get(i.getCreatedBy())));
		});
		return result;
	}
}
