package com.estsoft.projectdose.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UsersController.class)
public class UsersControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSignUp() throws Exception {
		mockMvc.perform(post("/api/auth/signup")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"test@example.com\",\"password\":\"1234\",\"nickname\":\"testuser\",\"name\":\"Test User\"}"))
			.andExpect(status().isCreated());
	}
}
