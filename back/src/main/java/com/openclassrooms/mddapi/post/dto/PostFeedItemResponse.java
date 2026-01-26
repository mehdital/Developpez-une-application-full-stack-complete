package com.openclassrooms.mddapi.post.dto;

import java.time.Instant;

public class PostFeedItemResponse {

	private final Long id;
	private final String title;
	private final String authorUsername;
	private final Instant createdAt;
	private final String content;

	public PostFeedItemResponse(Long id, String title, String authorUsername, Instant createdAt, String content) {
		this.id = id;
		this.title = title;
		this.authorUsername = authorUsername;
		this.createdAt = createdAt;
		this.content = content;
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

	public String getContent() {
		return content;
	}
}

