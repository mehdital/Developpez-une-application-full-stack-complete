package com.openclassrooms.mddapi.user;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.security.AuthenticatedUser;
import com.openclassrooms.mddapi.user.dto.UpdateMeRequest;
import com.openclassrooms.mddapi.user.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public UserResponse me(@AuthenticationPrincipal AuthenticatedUser user) {
		return userService.getMe(user.getId());
	}

	@PutMapping("/me")
	public UserResponse updateMe(@AuthenticationPrincipal AuthenticatedUser user, @Valid @RequestBody UpdateMeRequest request) {
		return userService.updateMe(user.getId(), request);
	}
}

