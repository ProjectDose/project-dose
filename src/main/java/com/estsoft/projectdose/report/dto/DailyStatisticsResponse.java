package com.estsoft.projectdose.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyStatisticsResponse {
	private Double overallAchievementRate;
	private List<LogDetails> doseLogDetails;
}