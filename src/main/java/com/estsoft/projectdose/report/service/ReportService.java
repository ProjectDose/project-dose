package com.estsoft.projectdose.report.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.repository.DoseLogRepository;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.report.dto.DailyStatisticsResponse;
import com.estsoft.projectdose.report.dto.LogDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final DoseLogRepository doseLogRepository;
	private final DoseScheduleRepository doseScheduleRepository;

	public Map<LocalDateTime, Integer> getMonthlyAchievementRates(Long userId) {
		// 월마다 일별 달성률 조회 
		List<Object[]> achievementRates = doseLogRepository.findMonthlyAchievementRates(userId);

		return achievementRates.stream()
			.collect(Collectors.toMap(
				result -> (LocalDateTime) result[0],  // 날짜
				result -> ((Number) result[1]).intValue()  // 달성률
			));
	}

	public DailyStatisticsResponse getDailyStatistics(Long userId, LocalDate selectedDate) {
		// 특정 날짜의 달성률 조회
		Double achievementRate = doseLogRepository.calculateDailyAchievementRate(userId, selectedDate);
		// 특정 날짜의 자세한 정보 조회
		List<LogDetails> doseLogDetails = doseLogRepository.findDailyDoseLogs(userId, selectedDate);

		return new DailyStatisticsResponse(achievementRate, doseLogDetails);
	}
}
