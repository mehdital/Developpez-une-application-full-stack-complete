package com.openclassrooms.mddapi.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsernameIgnoreCase(String username);

	Optional<UserEntity> findByEmailIgnoreCase(String email);

	Optional<UserEntity> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

	boolean existsByUsernameIgnoreCase(String username);

	boolean existsByEmailIgnoreCase(String email);

	@EntityGraph(attributePaths = "subscriptions")
	Optional<UserEntity> findOneWithSubscriptionsById(Long id);
}

