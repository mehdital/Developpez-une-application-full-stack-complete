package com.openclassrooms.mddapi.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.post.dto.CommentResponse;
import com.openclassrooms.mddapi.post.dto.CreateCommentRequest;
import com.openclassrooms.mddapi.post.dto.CreatePostRequest;
import com.openclassrooms.mddapi.post.dto.PostDetailResponse;
import com.openclassrooms.mddapi.post.dto.PostFeedItemResponse;
import com.openclassrooms.mddapi.security.AuthenticatedUser;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public List<PostFeedItemResponse> getFeed(@AuthenticationPrincipal AuthenticatedUser user, @RequestParam(value = "sort", required = false) String sort) {
		return postService.getFeed(user.getId(), sort);
	}

	@PostMapping
	public ResponseEntity<PostDetailResponse> createPost(@AuthenticationPrincipal AuthenticatedUser user, @Valid @RequestBody CreatePostRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(user.getId(), request));
	}

	@GetMapping("/{id}")
	public PostDetailResponse getPost(@PathVariable("id") Long id) {
		return postService.getPost(id);
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<CommentResponse> addComment(
			@AuthenticationPrincipal AuthenticatedUser user,
			@PathVariable("id") Long postId,
			@Valid @RequestBody CreateCommentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.addComment(user.getId(), postId, request));
	}
}

