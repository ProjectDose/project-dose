package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.service.UsersService;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RestController
@RequestMapping("/api/auth")
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
		usersService.signUp(signUpRequest);
		return ResponseEntity.ok("회원가입 성공");
	}

	@GetMapping("/checkEmail")
	public ResponseEntity<String> checkEmail(@RequestParam String email) {
		if (usersService.checkEmail(email)) {
			return ResponseEntity.ok("사용 가능한 이메일입니다.");
		} else {
			return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
		}
	}

	@GetMapping("/checkNickname")
	public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
		if (usersService.checkNickname(nickname)) {
			return ResponseEntity.ok("사용 가능한 닉네임입니다.");
		} else {
			return ResponseEntity.badRequest().body("중복된 닉네임입니다.");
		}
	}

	@GetMapping("/")
	public String mainPage() {
		return "main"; // main.html 파일을 렌더링
	}

	// @GetMapping("/login")
	// public String loginPage() {
	// 	return "login"; // login.html로 이동
	// }
}
