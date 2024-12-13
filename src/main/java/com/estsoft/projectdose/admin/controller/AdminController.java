package com.estsoft.projectdose.admin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.admin.service.AdminService;
import com.estsoft.projectdose.users.entity.Users;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/list")
	public ResponseEntity<List<Users>> getUserList() {
		return ResponseEntity.ok(adminService.getAllUsers());
	}

	@GetMapping("/search")
	public ResponseEntity<List<Users>> searchUsers(
		@RequestParam(required = false) String nickname,
		@RequestParam(required = false) String name
	) {
		if (nickname != null) {
			return ResponseEntity.ok(adminService.searchUsersByNickname(nickname));
		}
		if (name != null) {
			return ResponseEntity.ok(adminService.searchUsersByName(name));
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Users> getUserDetail(@PathVariable Long userId) {
		return ResponseEntity.ok(adminService.getUserDetail(userId));
	}

	@PostMapping("/{userId}/toggle-delete")
	public ResponseEntity<Void> toggleUserDelete(@PathVariable Long userId) {
		adminService.toggleUserDeleteStatus(userId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{userId}/reset-password")
	public ResponseEntity<Void> resetPassword(
		@PathVariable Long userId,
		@RequestBody String newPassword
	) {
		adminService.resetUserPassword(userId, newPassword);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/list")
	public ResponseEntity<Page<Users>> getUserList(
		@PageableDefault(page = 0, size = 10, sort = "joinDate", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return ResponseEntity.ok(adminService.getUserList(pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Users>> searchUsers(
		@RequestParam String searchTerm,
		@PageableDefault(page = 0, size = 10, sort = "joinDate", direction = Sort.Direction.DESC) Pageable pageable
	) {
		return ResponseEntity.ok(adminService.searchUsers(searchTerm, pageable));
	}
}
