package com.openclassrooms.mddapi.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreatePostRequest {

	@NotNull(message = "topicId is required")
	private Long topicId;

	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Content is required")
	private String content;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

