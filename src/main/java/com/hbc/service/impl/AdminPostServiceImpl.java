package com.hbc.service.impl;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbc.dto.post.AdminPostRequestDto;
import com.hbc.dto.post.AdminPostRequestUpdateDto;
import com.hbc.dto.post.PostResponseDto;
import com.hbc.entity.Post;
import com.hbc.entity.User;
import com.hbc.exception.post.InvalidTitleException;
import com.hbc.exception.post.PostNotFoundException;
import com.hbc.repo.PostRepo;
import com.hbc.repo.UserRepo;
import com.hbc.service.AdminPostService;
import com.hbc.util.VNCharUtil;

@Service
public class AdminPostServiceImpl implements AdminPostService {
	
	private static final int MAX_SLUG_LENGTH = 250;

	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public List<PostResponseDto> findAllAvailable() {
		List<Post> posts = postRepo.findByIsDeletedOrderByCreatedAtDesc(false);
		List<Long> authorIds = posts.stream().map(Post::getCreatedBy).toList();
		List<User> users = userRepo.findByIdIn(authorIds);
		Map<Long, String> mapAuthors = users.stream().collect(Collectors.toMap(User::getId, User::getUsername));

		List<PostResponseDto> result = new ArrayList<>();
		posts.forEach(i -> {
			result.add(PostResponseDto.build(i, mapAuthors.get(i.getCreatedBy())));
		});
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insert(AdminPostRequestDto requestDto, long currentUserId) {
		if (requestDto.getTitle().length() > 255) {
			throw new InvalidTitleException("400", "Tên bài viết không được dài quá 255 ký tự.");
		}
		
		String slug = generateSlug(requestDto.getTitle());
		
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		postRepo.insert(requestDto.getCategoryId(), requestDto.getTitle(), requestDto.getContent(), slug,
				requestDto.getImgUrl(), createdAt, currentUserId, false);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(AdminPostRequestUpdateDto requestDto, long currentUserId) throws Exception {
		Post post = postRepo.findByIdAndIsDeleted(requestDto.getId(), false);
		if (post == null) {
			throw new PostNotFoundException("404", "Không tìm thấy bài viết.");
		}

		if (requestDto.getTitle().length() > 255) {
			throw new InvalidTitleException("400", "Tên bài viết không được dài quá 255 ký tự.");
		}
		
		post.setContent(requestDto.getContent());
		post.setImgUrl(requestDto.getImgUrl());
		post.setTitle(requestDto.getTitle());
		String slug = generateSlug(requestDto.getTitle());

		Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
		postRepo.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl(), slug, updatedAt,
				currentUserId, requestDto.getId());
	}
	
	private String generateSlug(String title) {
		String slug = Normalizer.normalize(title.toLowerCase(), Normalizer.Form.NFD);
		slug = slug.replaceAll("[^\\p{L}\\d\\s:]", "");
		slug = slug.replaceAll(":", "");
		slug = slug.replaceAll("\\s+", "-");
		slug = VNCharUtil.removeAccent(slug);

		if (slug.length() > MAX_SLUG_LENGTH) {
			slug = slug.substring(0, MAX_SLUG_LENGTH);
		}

		int version = 1;
		String baseSlug = slug;
		while (postRepo.existsBySlug(slug)) {
			slug = baseSlug + "-v" + version++;
			if (slug.length() > MAX_SLUG_LENGTH) {
				slug = slug.substring(0, MAX_SLUG_LENGTH);
			}
		}

		return slug;
	}

	@Override
	public PostResponseDto findById(long id) throws Exception {
		Post post = postRepo.findByIdAndIsDeleted(id, false);
		if (post == null) {
			throw new PostNotFoundException("404", "Không tìm thấy bài viết.");
		}
		User author = userRepo.findById(post.getCreatedBy()).get();
		return PostResponseDto.build(post, author.getUsername());
	}
}
