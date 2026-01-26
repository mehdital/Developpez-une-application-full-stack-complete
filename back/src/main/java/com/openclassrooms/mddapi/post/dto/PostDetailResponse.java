package com.openclassrooms.mddapi.post.dto;

import java.time.Instant;
import java.util.List;

import com.openclassrooms.mddapi.topic.dto.TopicSummaryResponse;

public class PostDetailResponse {

	private final Long id;
	private final String title;
	private final String authorUsername;
	private final Instant createdAt;
	private final TopicSummaryResponse topic;
	private final String content;
	private final List<CommentResponse> comments;

	public PostDetailResponse(
			Long id,
			String title,
			String authorUsername,
			Instant createdAt,
			TopicSummaryResponse topic,
			String content,
			List<CommentResponse> comments) {
		this.id = id;
		this.title = title;
		this.authorUsername = authorUsername;
		this.createdAt = createdAt;
		this.topic = topic;
		this.content = content;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public TopicSummaryResponse getTopic() {
		return topic;
	}

	public String getContent() {
		return content;
	}

	public List<CommentResponse> getComments() {
		return comments;
	}
}

