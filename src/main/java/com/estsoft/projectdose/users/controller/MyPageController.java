package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class MyPageController {

	private final UsersService usersService;

	public MyPageController(UsersService usersService) {
		this.usersService = usersService;
	}


	@GetMapping("/{userId}/myPage")
	public ResponseEntity<?> getMyPage(@PathVariable Long userId) {
		try {
			Users user = usersService.findUserById(userId);
			return ResponseEntity.ok(Map.of(
				"nickname", user.getNickname(),
				"joinDate", user.getJoinDate()
			));
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody Map<String, String> updates) {
		if (userId == null) {
			return ResponseEntity.status(400).body(Map.of("message", "잘못된 사용자 ID입니다."));
		}
		try {
			usersService.updateUser(userId, updates);
			return ResponseEntity.ok(Map.of("message", "변경이 완료되었습니다."));
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		try {
			usersService.deleteUser(userId);
			return ResponseEntity.ok(Map.of("message", "회원 탈퇴가 완료되었습니다."));
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
		}
	}
}
