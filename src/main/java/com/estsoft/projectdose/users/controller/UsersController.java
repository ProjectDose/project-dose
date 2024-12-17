package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.service.UsersService;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@GetMapping("/signup")
	public String signUpPage() {
		return "signup";
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		usersService.signUp(signUpRequest);
		return ResponseEntity.ok("회원가입 성공");
	}

	@GetMapping("/checkEmailDuplicate")
	public ResponseEntity<Map<String, String>> checkEmail(@RequestParam String email) {
		Map<String, String> response = new HashMap<>();
		if (usersService.checkEmail(email)) {
			response.put("message", "사용 가능한 이메일입니다.");
		} else {
			response.put("message", "이미 존재하는 이메일입니다.");
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/checkNicknameDuplicate")
	public ResponseEntity<Map<String, String>> checkNickname(@RequestParam String nickname) {
		Map<String, String> response = new HashMap<>();
		if (usersService.checkNickname(nickname)) {
			response.put("message", "사용 가능한 닉네임입니다.");
		} else {
			response.put("message", "중복된 닉네임입니다.");
		}
		return ResponseEntity.ok(response);
	}
}
