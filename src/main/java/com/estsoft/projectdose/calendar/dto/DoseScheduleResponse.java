package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
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
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseScheduleResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		//시큐리티 적용된 userId 가져오기
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
	public static DoseScheduleResponse fromEntity(DoseSchedule doseSchedule) {
		return new DoseScheduleResponse(
			doseSchedule.getScheduleId(),
			doseSchedule.getUsers().getId(),
			doseSchedule.getMedicationName(),
			doseSchedule.getDoseTime(),
			doseSchedule.getDosage(),
			doseSchedule.getRepeatInterval(),
			doseSchedule.getDaysOfWeek(),
			doseSchedule.getStartDate()
		);
	}
}
