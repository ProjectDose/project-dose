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
import com.estsoft.projectdose.users.entity.Users;

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

	@GetMapping({"/reset-password", "/api/auth/reset-password"})
	public ModelAndView showResetPasswordPage(@RequestParam("token") String token) {
		ModelAndView mav = new ModelAndView("reset-password");

		boolean isValidToken = usersService.validateResetToken(token);
		if (!isValidToken) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "유효하지 않거나 만료된 링크입니다.");
			return mav;
		}

		mav.addObject("token", token);
		return mav;
	}

	@PostMapping("/resetPassword")
	public ModelAndView resetPassword(@Valid @ModelAttribute PasswordResetRequest passwordResetRequest) {
		ModelAndView mav = new ModelAndView();
		try {
			Users user = usersService.findUserByResetToken(passwordResetRequest.getToken());

			if (usersService.isSameAsOldPassword(user, passwordResetRequest.getNewPassword())) {
				mav.setViewName("reset-password");
				mav.addObject("errorMessage", "이전에 설정한 비밀번호입니다.");
				return mav;
			}

			usersService.resetPassword(passwordResetRequest);
			mav.setViewName("reset-password-success");
			mav.addObject("successMessage", "비밀번호가 변경되었습니다.");
		} catch (RuntimeException e) {
			mav.setViewName("error");
			mav.addObject("errorMessage", e.getMessage());
		}
		return mav;
	}
}
