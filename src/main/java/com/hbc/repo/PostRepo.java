package com.hbc.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hbc.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findByIsDeletedOrderByCreatedAtDesc(boolean isDeleted);
	Post findBySlugAndIsDeleted(String slug, boolean isDeleted);
	Post findByIdAndIsDeleted(long id, boolean isDeleted);
	boolean existsBySlug(String slug);

	@Modifying
	@Query(value = "INSERT INTO post(category_id, title, content, slug, img_url, created_at, created_by, is_deleted) "
			+ "VALUE (:categoryId, :title, :content, :slug, :imgUrl, :createdAt, :createdBy, :isDeleted)", nativeQuery = true)
	void insert(@Param("categoryId") long categoryId, @Param("title") String title, @Param("content") String content,
			@Param("slug") String slug, @Param("imgUrl") String imgUrl, @Param("createdAt") Timestamp createdAt,
			@Param("createdBy") long createdBy, @Param("isDeleted") boolean isDeleted);

	@Modifying
	@Query(value = "UPDATE post SET title = :title, content = :content, img_url = :imgUrl, slug = :slug,"
			+ " :updatedAt, :updatedBy) WHERE id = :id", nativeQuery = true)
	void update(@Param("title") String title, @Param("content") String content, @Param("imgUrl") String imgUrl,
			@Param("slug") String slug, @Param("updatedAt") Timestamp updatedAt,
			@Param("updatedBy") long updatedBy, @Param("id") long id);
}
