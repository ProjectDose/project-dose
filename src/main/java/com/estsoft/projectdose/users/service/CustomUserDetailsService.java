package com.estsoft.projectdose.users.service;

import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	private final UsersRepository usersRepository;

	public CustomUserDetailsService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("Attempting to load user by email: {}", email);

		Users user = usersRepository.findByEmail(email)
			.orElseThrow(() -> {
				logger.warn("User not found with email: {}", email);
				return new UsernameNotFoundException("User not found with email: " + email);
			});

		logger.info("Successfully loaded user: {}", email);

		return new User(user.getEmail(), user.getPassword(), Collections.singleton(user.getRole().getAuthority()));
	}
}