package com.estsoft.projectdose.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UsersRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Users getUserDetail(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Transactional
	public void toggleUserDeleteStatus(Long userId) {
		Users user = getUserDetail(userId);
		user.setDeleted(!user.isDeleted());
	}

	@Transactional
	public void resetUserPassword(Long userId, String newPassword) {
		Users user = getUserDetail(userId);
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
	}

	public Page<Users> getUserList(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public Page<Users> searchUsers(String searchTerm, Pageable pageable) {
		return userRepository.findByNicknameContainingOrNameContaining(searchTerm, searchTerm, pageable);
	}
}
