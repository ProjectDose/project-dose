package com.estsoft.projectdose.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyStatisticsResponse {
	private Double AchievementRate;
	private List<LogDetails> doseLogDetails;
}