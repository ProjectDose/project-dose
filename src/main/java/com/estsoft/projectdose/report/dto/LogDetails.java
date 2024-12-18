package com.estsoft.projectdose.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class LogDetails {
	private String medicationName;
	private LocalTime takenTime;
	private LocalTime doseTime; // localTime -> localDateTime 변경
	private Boolean taken;
}