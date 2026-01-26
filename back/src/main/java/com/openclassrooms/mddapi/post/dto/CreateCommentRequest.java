package com.openclassrooms.mddapi.post.dto;

import javax.validation.constraints.NotBlank;

public class CreateCommentRequest {

	@NotBlank(message = "Content is required")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

