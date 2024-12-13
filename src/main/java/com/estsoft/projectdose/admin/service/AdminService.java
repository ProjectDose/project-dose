package com.estsoft.projectdose.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UsersRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}

	public List<Users> searchUsersByNickname(String nickname) {
		return userRepository.findByNicknameContaining(nickname);
	}

	public List<Users> searchUsersByName(String name) {
		return userRepository.findByNameContaining(name);
	}

	public Users getUserDetail(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
	}

	public void toggleUserDeleteStatus(Long userId) {
		Users user = getUserDetail(userId);
		user.setDeleted(!user.isDeleted());
		userRepository.save(user);
	}

	public void resetUserPassword(Long userId, String newPassword) {
		Users user = getUserDetail(userId);
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public Page<Users> getUserList(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public Page<Users> searchUsers(String searchTerm, Pageable pageable) {
		return userRepository.findByNicknameContainingOrNameContaining(searchTerm, searchTerm, pageable);
	}
}
