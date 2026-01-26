package com.openclassrooms.mddapi.user.dto;

import java.util.List;

import com.openclassrooms.mddapi.topic.dto.TopicSummaryResponse;

public class UserResponse {

	private final Long id;
	private final String username;
	private final String email;
	private final List<TopicSummaryResponse> subscriptions;

	public UserResponse(Long id, String username, String email, List<TopicSummaryResponse> subscriptions) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.subscriptions = subscriptions;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public List<TopicSummaryResponse> getSubscriptions() {
		return subscriptions;
	}
}

