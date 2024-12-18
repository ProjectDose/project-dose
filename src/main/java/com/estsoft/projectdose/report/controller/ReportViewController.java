package com.estsoft.projectdose.report.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.projectdose.report.dto.DailyStatisticsResponse;
import com.estsoft.projectdose.report.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportViewController {
	private final ReportService reportService;

	@GetMapping("/statistics")
	public String getDoseStatistics(Model model){
		Map<LocalDateTime, Integer> monthlyAchievementRates =
			reportService.getMonthlyAchievementRates();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String achievementRatesJson = objectMapper.writeValueAsString(
				monthlyAchievementRates.entrySet().stream()
					.collect(Collectors.toMap(
						entry -> entry.getKey().toString(),  // LocalDate.toString() 사용
						Map.Entry::getValue
					))
			);
			model.addAttribute("achievementRatesJson", achievementRatesJson);
			return "doseStatistics";
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/statistics/daily-details")
	public String getDailyStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate, Model model) {

		DailyStatisticsResponse dailyStatistics =
			reportService.getDailyStatistics(selectedDate);

		model.addAttribute("dailyStatistics", dailyStatistics);
		return "doseDailyDetails";
	}

}
