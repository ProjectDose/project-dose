package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.service.UsersService;
import com.estsoft.projectdose.users.dto.PasswordFindRequest;
import com.estsoft.projectdose.users.dto.PasswordResetRequest;
import com.estsoft.projectdose.users.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
	public ModelAndView signUp(@Valid @ModelAttribute SignUpRequest signUpRequest) {
		usersService.signUp(signUpRequest);
		ModelAndView mav = new ModelAndView("signup");
		mav.addObject("successMessage", "회원가입이 완료되었습니다. 가입한 이메일로 로그인 해주세요.");
		return mav;
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
	@PostMapping("/findPassword")
	public ResponseEntity<String> findPassword(@RequestBody PasswordFindRequest passwordFindRequest) {
		usersService.findPassword(passwordFindRequest);
		return ResponseEntity.ok("비밀번호 재설정 이메일이 전송되었습니다.");
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
		usersService.resetPassword(passwordResetRequest);
		return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
	}
}
