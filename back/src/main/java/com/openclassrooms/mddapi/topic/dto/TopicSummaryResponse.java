package com.openclassrooms.mddapi.topic.dto;

public class TopicSummaryResponse {

	private final Long id;
	private final String title;
	private final String description;

	public TopicSummaryResponse(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}

