package com.openclassrooms.mddapi.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthFlowIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void registerThenLogin_returnsTokenAndUser() throws Exception {
		String username = "user_" + UUID.randomUUID().toString().replace("-", "");
		String email = username + "@example.com";
		String password = "Test1234!";

		String registerBody = String.format(
				"{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
				username, email, password);

		mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registerBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").value(username))
				.andExpect(jsonPath("$.email").value(email))
				.andExpect(jsonPath("$.subscriptions").isArray());

		String loginBody = String.format(
				"{\"identifier\":\"%s\",\"password\":\"%s\"}",
				username, password);

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.user.id").exists())
				.andExpect(jsonPath("$.user.username").value(username))
				.andExpect(jsonPath("$.user.email").value(email))
				.andExpect(jsonPath("$.user.subscriptions").isArray());
	}
}

