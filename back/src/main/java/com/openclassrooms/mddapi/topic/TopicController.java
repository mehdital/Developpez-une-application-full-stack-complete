package com.openclassrooms.mddapi.topic;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.security.AuthenticatedUser;
import com.openclassrooms.mddapi.topic.dto.TopicResponse;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

	private final TopicService topicService;

	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@GetMapping
	public List<TopicResponse> listTopics(@AuthenticationPrincipal AuthenticatedUser user) {
		return topicService.listTopics(user.getId());
	}

	@PostMapping("/{id}/subscribe")
	public ResponseEntity<Void> subscribe(@AuthenticationPrincipal AuthenticatedUser user, @PathVariable("id") Long topicId) {
		topicService.subscribe(user.getId(), topicId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/subscribe")
	public ResponseEntity<Void> unsubscribe(@AuthenticationPrincipal AuthenticatedUser user, @PathVariable("id") Long topicId) {
		topicService.unsubscribe(user.getId(), topicId);
		return ResponseEntity.noContent().build();
	}
}

