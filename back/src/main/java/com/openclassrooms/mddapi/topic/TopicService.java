package com.openclassrooms.mddapi.topic;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.common.NotFoundException;
import com.openclassrooms.mddapi.topic.dto.TopicResponse;
import com.openclassrooms.mddapi.user.UserEntity;
import com.openclassrooms.mddapi.user.UserRepository;

@Service
public class TopicService {

	private final TopicRepository topicRepository;
	private final UserRepository userRepository;

	public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
		this.topicRepository = topicRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public List<TopicResponse> listTopics(Long userId) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		Set<Long> subscribedTopicIds = user.getSubscriptions().stream().map(TopicEntity::getId).collect(Collectors.toSet());

		return topicRepository.findAll().stream()
				.sorted(Comparator.comparing(TopicEntity::getTitle, String.CASE_INSENSITIVE_ORDER))
				.map(topic -> new TopicResponse(topic.getId(), topic.getTitle(), topic.getDescription(), subscribedTopicIds.contains(topic.getId())))
				.collect(Collectors.toList());
	}

	@Transactional
	public void subscribe(Long userId, Long topicId) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		TopicEntity topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new NotFoundException("Topic not found"));

		if (!user.getSubscriptions().contains(topic)) {
			user.getSubscriptions().add(topic);
			userRepository.save(user);
		}
	}

	@Transactional
	public void unsubscribe(Long userId, Long topicId) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		TopicEntity topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new NotFoundException("Topic not found"));

		if (user.getSubscriptions().contains(topic)) {
			user.getSubscriptions().remove(topic);
			userRepository.save(user);
		}
	}
}

