package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoseScheduleResponse {
	private Long scheduleId;
	private Long users;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseScheduleResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		this.users = doseSchedule.getUsers().getId();
		this.medicationName = doseSchedule.getMedicationName();
		this.doseTime = doseSchedule.getDoseTime();
		this.dosage = doseSchedule.getDosage();
		this.repeatInterval = doseSchedule.getRepeatInterval();
		this.daysOfWeek = doseSchedule.getDaysOfWeek();
		this.startDate = doseSchedule.getStartDate();
	}
}
