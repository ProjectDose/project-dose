package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDoseScheduleRequest {
	private String doseTime;
	private String medicationName;
	private String dosage;
}
