package com.estsoft.projectdose.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarControl {
	//메인페이지 이동
	@GetMapping("/")
	public String home() {
		return "Calendar";
	}
}