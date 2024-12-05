package com.estsoft.projectdose.report.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.projectdose.report.dto.DailyStatisticsResponse;
import com.estsoft.projectdose.report.service.ReportService;
import com.estsoft.projectdose.users.entity.Users;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReportViewController {
	private final ReportService reportService;

	@GetMapping("/statistics")
	public String getDoseStatistics(Model model) {
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Users users = (Users)authentication.getPrincipal();

		Map<LocalDateTime, Integer> monthlyAchievementRates =
			reportService.getMonthlyAchievementRates(1L);

		model.addAttribute("monthlyAchievementRates", monthlyAchievementRates);
		return "dose-statistics";
	}

	@GetMapping("/statistics/dailyDetails")
	public String getDailyStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate, Model model) {
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Users users = (Users)authentication.getPrincipal();

		DailyStatisticsResponse dailyStatistics =
			reportService.getDailyStatistics(1L, selectedDate);

		model.addAttribute("dailyStatistics", dailyStatistics);
		return "dose-daily-details :: #dailyDetailsFragment";
	}

}
