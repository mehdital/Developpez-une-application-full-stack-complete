package com.openclassrooms.mddapi.post;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.common.BadRequestException;
import com.openclassrooms.mddapi.common.NotFoundException;
import com.openclassrooms.mddapi.post.dto.CommentResponse;
import com.openclassrooms.mddapi.post.dto.CreateCommentRequest;
import com.openclassrooms.mddapi.post.dto.CreatePostRequest;
import com.openclassrooms.mddapi.post.dto.PostDetailResponse;
import com.openclassrooms.mddapi.post.dto.PostFeedItemResponse;
import com.openclassrooms.mddapi.topic.TopicEntity;
import com.openclassrooms.mddapi.topic.TopicRepository;
import com.openclassrooms.mddapi.topic.dto.TopicSummaryResponse;
import com.openclassrooms.mddapi.user.UserEntity;
import com.openclassrooms.mddapi.user.UserRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final TopicRepository topicRepository;
	private final UserRepository userRepository;

	public PostService(PostRepository postRepository, CommentRepository commentRepository, TopicRepository topicRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.topicRepository = topicRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public List<PostFeedItemResponse> getFeed(Long userId, String sort) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		Set<Long> topicIds = user.getSubscriptions().stream().map(TopicEntity::getId).collect(Collectors.toSet());
		if (topicIds.isEmpty()) {
			return Collections.emptyList();
		}

		Sort.Direction direction = toDirection(sort);
		List<PostEntity> posts = postRepository.findByTopicIdIn(List.copyOf(topicIds), Sort.by(direction, "createdAt"));

		return posts.stream()
				.map(p -> new PostFeedItemResponse(p.getId(), p.getTitle(), p.getAuthor().getUsername(), p.getCreatedAt(), p.getContent()))
				.collect(Collectors.toList());
	}

	@Transactional
	public PostDetailResponse createPost(Long userId, CreatePostRequest request) {
		UserEntity author = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		TopicEntity topic = topicRepository.findById(request.getTopicId())
				.orElseThrow(() -> new NotFoundException("Topic not found"));

		PostEntity post = new PostEntity();
		post.setAuthor(author);
		post.setTopic(topic);
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());

		PostEntity saved = postRepository.save(post);
		return toPostDetailResponse(saved, List.of());
	}

	@Transactional(readOnly = true)
	public PostDetailResponse getPost(Long postId) {
		PostEntity post = postRepository.findOneWithAuthorAndTopicById(postId)
				.orElseThrow(() -> new NotFoundException("Post not found"));

		List<CommentResponse> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream()
				.map(c -> new CommentResponse(c.getId(), c.getAuthor().getUsername(), c.getCreatedAt(), c.getContent()))
				.collect(Collectors.toList());

		return toPostDetailResponse(post, comments);
	}

	@Transactional
	public CommentResponse addComment(Long userId, Long postId, CreateCommentRequest request) {
		UserEntity author = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		PostEntity post = postRepository.findById(postId)
				.orElseThrow(() -> new NotFoundException("Post not found"));

		CommentEntity comment = new CommentEntity();
		comment.setAuthor(author);
		comment.setPost(post);
		comment.setContent(request.getContent());
		CommentEntity saved = commentRepository.save(comment);

		return new CommentResponse(saved.getId(), author.getUsername(), saved.getCreatedAt(), saved.getContent());
	}

	private static Sort.Direction toDirection(String sort) {
		if (sort == null || sort.isBlank() || sort.equalsIgnoreCase("desc")) {
			return Sort.Direction.DESC;
		}
		if (sort.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		}
		throw new BadRequestException("Invalid sort value (expected asc|desc)");
	}

	private static PostDetailResponse toPostDetailResponse(PostEntity post, List<CommentResponse> comments) {
		TopicEntity topic = post.getTopic();
		TopicSummaryResponse topicSummary = new TopicSummaryResponse(topic.getId(), topic.getTitle(), topic.getDescription());

		return new PostDetailResponse(
				post.getId(),
				post.getTitle(),
				post.getAuthor().getUsername(),
				post.getCreatedAt(),
				topicSummary,
				post.getContent(),
				comments);
	}
}
