package com.openclassrooms.mddapi.post;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	@EntityGraph(attributePaths = "author")
	List<CommentEntity> findByPostIdOrderByCreatedAtAsc(Long postId);
}

