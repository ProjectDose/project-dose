package com.estsoft.projectdose.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

	@GetMapping("/error")
	public String handleError() {
		return "error";
	}
}
