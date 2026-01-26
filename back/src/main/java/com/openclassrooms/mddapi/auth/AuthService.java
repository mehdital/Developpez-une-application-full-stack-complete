package com.openclassrooms.mddapi.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.auth.dto.AuthResponse;
import com.openclassrooms.mddapi.auth.dto.LoginRequest;
import com.openclassrooms.mddapi.auth.dto.RegisterRequest;
import com.openclassrooms.mddapi.common.BadRequestException;
import com.openclassrooms.mddapi.common.UnauthorizedException;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.user.UserEntity;
import com.openclassrooms.mddapi.user.UserRepository;
import com.openclassrooms.mddapi.user.UserService;
import com.openclassrooms.mddapi.user.dto.UserResponse;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Transactional
	public UserResponse register(RegisterRequest request) {
		if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
			throw new BadRequestException("Username already in use");
		}
		if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
			throw new BadRequestException("Email already in use");
		}

		UserEntity user = new UserEntity();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		UserEntity saved = userRepository.save(user);

		return userService.toUserResponse(saved);
	}

	@Transactional(readOnly = true)
	public AuthResponse login(LoginRequest request) {
		UserEntity user = userRepository
				.findByUsernameIgnoreCaseOrEmailIgnoreCase(request.getIdentifier(), request.getIdentifier())
				.orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			throw new UnauthorizedException("Invalid credentials");
		}

		String token = jwtService.generateToken(user);
		return new AuthResponse(token, userService.toUserResponse(user));
	}
}
