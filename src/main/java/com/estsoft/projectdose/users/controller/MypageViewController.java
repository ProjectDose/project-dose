package com.estsoft.projectdose.users.controller;

import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageViewController {

	private final UsersService usersService;

	public MypageViewController(UsersService usersService) {
		this.usersService = usersService;
	}

	@GetMapping
	public String getMyPageView(Model model, Authentication authentication) {
		if (authentication == null) {
			throw new RuntimeException("사용자가 로그인되어 있지 않습니다.");
		}

		String userEmail = authentication.getName();
		Users user = usersService.findUserByEmail(userEmail);
		System.out.println("로그인한 사용자 ID: " + user.getId());

		model.addAttribute("id", user.getId());
		model.addAttribute("nickname", user.getNickname());
		model.addAttribute("joinDate", user.getJoinDate().toLocalDate());

		return "mypage";
	}

	@GetMapping("/change-nickname")
	public String getChangeNicknameView(Model model, Authentication authentication) {

		String userEmail = authentication.getName();

		Users user = usersService.findUserByEmail(userEmail);

		model.addAttribute("id", user.getId());
		model.addAttribute("nickname", user.getNickname());

		return "change-nickname";
	}

	@GetMapping("/change-password")
	public String getChangePasswordView(Model model, Authentication authentication) {
		String userEmail = authentication.getName();
		Users user = usersService.findUserByEmail(userEmail);

		model.addAttribute("id", user.getId());
		return "change-password";
	}
}
