package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoseScheduleViewResponse {
	private Long scheduleId;
	private Long userId;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseScheduleViewResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			this.userId = Long.valueOf(((UserDetails)principal).getUsername());
		}else{
			this.userId = Long.valueOf(principal.toString());
		}
		this.medicationName = doseSchedule.getMedicationName();
		this.doseTime = doseSchedule.getDoseTime();
		this.dosage = doseSchedule.getDosage();
		this.repeatInterval = doseSchedule.getRepeatInterval();
		this.daysOfWeek = doseSchedule.getDaysOfWeek();
		this.startDate = doseSchedule.getStartDate();
	}
}
