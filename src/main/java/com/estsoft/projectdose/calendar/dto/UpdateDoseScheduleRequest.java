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
	private Long scheduleId;
	private Long userId;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public UpdateDoseScheduleRequest(Long scheduleId,String medicationName,Map<String,Object> doseTime,String dosage,int repeatInterval,Map<String,Object> daysOfWeek,
		LocalDate startDate) {
		this.scheduleId = scheduleId;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			this.userId = Long.valueOf(((UserDetails)principal).getUsername());
		}else{
			this.userId = Long.valueOf(principal.toString());
		}
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.dosage = dosage;
		this.repeatInterval = repeatInterval;
		this.daysOfWeek = daysOfWeek;
		this.startDate = startDate;
	}

	public Optional<Object> findById(Long id) {
		return Optional.ofNullable(doseTime.get(id.toString()));
	}
}
