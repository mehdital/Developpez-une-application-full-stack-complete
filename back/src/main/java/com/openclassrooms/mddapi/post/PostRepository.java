package com.openclassrooms.mddapi.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

	@EntityGraph(attributePaths = { "author", "topic" })
	List<PostEntity> findByTopicIdIn(List<Long> topicIds, Sort sort);

	@EntityGraph(attributePaths = { "author", "topic" })
	Optional<PostEntity> findOneWithAuthorAndTopicById(Long id);
}

