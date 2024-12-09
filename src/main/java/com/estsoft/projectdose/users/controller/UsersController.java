package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.dto.PasswordFindRequest;
import com.estsoft.projectdose.users.dto.PasswordResetRequest;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import com.estsoft.projectdose.users.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		usersService.signUp(signUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
	}

	@GetMapping("/checkEmailDuplicate")
	public ResponseEntity<String> checkEmailDuplicate(@RequestParam String email) {
		if (usersService.checkEmail(email)) {
			return ResponseEntity.ok("사용 가능한 이메일입니다.");
		} else {
			return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
		}
	}

	@GetMapping("/checkNicknameDuplicate")
	public ResponseEntity<String> checkNicknameDuplicate(@RequestParam String nickname) {
		if (usersService.checkNickname(nickname)) {
			return ResponseEntity.ok("사용 가능한 닉네임입니다.");
		} else {
			return ResponseEntity.badRequest().body("중복된 닉네임입니다.");
		}
	}

	@PostMapping("/findPassword")
	public ResponseEntity<String> findPassword(@RequestBody PasswordFindRequest passwordFindRequest) {
		usersService.findPassword(passwordFindRequest);
		return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
		usersService.resetPassword(passwordResetRequest);
		return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
	}
}
