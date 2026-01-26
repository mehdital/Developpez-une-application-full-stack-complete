package com.openclassrooms.mddapi.topic.dto;

public class TopicResponse {

	private final Long id;
	private final String title;
	private final String description;
	private final boolean subscribed;

	public TopicResponse(Long id, String title, String description, boolean subscribed) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.subscribed = subscribed;
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

	public boolean isSubscribed() {
		return subscribed;
	}
}

