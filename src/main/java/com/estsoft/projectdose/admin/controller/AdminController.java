package com.estsoft.projectdose.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.estsoft.projectdose.admin.dto.PasswordResetRequest;
import com.estsoft.projectdose.admin.dto.UserListResponse;
import com.estsoft.projectdose.admin.service.AdminService;
import com.estsoft.projectdose.users.entity.Users;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/list")
	public ResponseEntity<Page<UserListResponse>> getUserList(
		@PageableDefault(page = 0, size = 10, sort = "joinDate", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<Users> usersPage = adminService.getUserList(pageable);
		Page<UserListResponse> dtoPage = usersPage.map(UserListResponse::from);
		return ResponseEntity.ok(dtoPage);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<UserListResponse>> searchUsers(
		@RequestParam String searchTerm,
		@PageableDefault(page = 0, size = 10, sort = "joinDate", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<Users> usersPage = adminService.searchUsers(searchTerm, pageable);
		Page<UserListResponse> dtoPage = usersPage.map(UserListResponse::from);
		return ResponseEntity.ok(dtoPage);
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
		@RequestBody PasswordResetRequest request) {
		adminService.resetUserPassword(userId, request.getPassword());
		return ResponseEntity.ok().build();
	}

}
