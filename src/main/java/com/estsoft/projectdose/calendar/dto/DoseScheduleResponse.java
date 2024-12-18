package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.service.UsersService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DoseScheduleResponse {
	private Long scheduleId;
	private Long userId;
	private String medicationName;
	private String doseTime;
	private String dosage;
	private int repeatInterval;
	private String daysOfWeek;
	private LocalDate startDate;

	public static DoseScheduleResponse fromEntity(DoseSchedule doseSchedule) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = null;
		if (principal instanceof org.springframework.security.core.userdetails.User) {
			String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
		} else if (principal instanceof com.estsoft.projectdose.users.entity.Users) {
			userId = ((com.estsoft.projectdose.users.entity.Users) principal).getId();
		}
		return new DoseScheduleResponse(
			doseSchedule.getScheduleId(),
			userId,
			doseSchedule.getMedicationName(),
			doseSchedule.getDoseTime().get("time").toString(),
			doseSchedule.getDosage(),
			doseSchedule.getRepeatInterval(),
			doseSchedule.getDaysOfWeek().get("day").toString(),
			doseSchedule.getStartDate()
		);
	}
	public DoseScheduleResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		this.doseTime = doseSchedule.getDoseTime().toString();  // JsonNode의 경우 toString()으로 String으로 변환
		this.medicationName = doseSchedule.getMedicationName();
		this.dosage = doseSchedule.getDosage();
	}
}
