package com.estsoft.projectdose.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

	@GetMapping("/users")
	public String adminUserPage() {
		return "adminUsers";
	}
}