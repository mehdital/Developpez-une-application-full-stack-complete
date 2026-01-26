package com.openclassrooms.mddapi.post.dto;

import java.time.Instant;

public class CommentResponse {

	private final Long id;
	private final String authorUsername;
	private final Instant createdAt;
	private final String content;

	public CommentResponse(Long id, String authorUsername, Instant createdAt, String content) {
		this.id = id;
		this.authorUsername = authorUsername;
		this.createdAt = createdAt;
		this.content = content;
	}

	public Long getId() {
		return id;
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

