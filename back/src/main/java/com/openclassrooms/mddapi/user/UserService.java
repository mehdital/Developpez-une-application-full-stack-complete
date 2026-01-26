package com.openclassrooms.mddapi.user;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.common.BadRequestException;
import com.openclassrooms.mddapi.common.NotFoundException;
import com.openclassrooms.mddapi.common.PasswordValidation;
import com.openclassrooms.mddapi.topic.TopicEntity;
import com.openclassrooms.mddapi.topic.dto.TopicSummaryResponse;
import com.openclassrooms.mddapi.user.dto.UpdateMeRequest;
import com.openclassrooms.mddapi.user.dto.UserResponse;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
	public UserResponse getMe(Long userId) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return toUserResponse(user);
	}

	@Transactional
	public UserResponse updateMe(Long userId, UpdateMeRequest request) {
		UserEntity user = userRepository.findOneWithSubscriptionsById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		if (!user.getUsername().equalsIgnoreCase(request.getUsername())
				&& userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
			throw new BadRequestException("Username already in use");
		}

		if (!user.getEmail().equalsIgnoreCase(request.getEmail())
				&& userRepository.existsByEmailIgnoreCase(request.getEmail())) {
			throw new BadRequestException("Email already in use");
		}

		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			if (!request.getPassword().matches(PasswordValidation.REGEX)) {
				throw new BadRequestException("Password must be >=8 with lower/upper/digit/special");
			}
			user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		}

		UserEntity saved = userRepository.save(user);
		return toUserResponse(saved);
	}

	public UserResponse toUserResponse(UserEntity user) {
		List<TopicSummaryResponse> subscriptions = user.getSubscriptions().stream()
				.sorted(Comparator.comparing(TopicEntity::getTitle, String.CASE_INSENSITIVE_ORDER))
				.map(t -> new TopicSummaryResponse(t.getId(), t.getTitle(), t.getDescription()))
				.collect(Collectors.toList());

		return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), subscriptions);
	}
}
