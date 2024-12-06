package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoseScheduleViewResponse {
	private Long scheduleId;
	private Users users;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private Date startDate;

	public DoseScheduleViewResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		this.users = doseSchedule.getUsers();
		this.medicationName = doseSchedule.getMedicationName();
		this.doseTime = doseSchedule.getDoseTime();
		this.dosage = doseSchedule.getDosage();
		this.repeatInterval = doseSchedule.getRepeatInterval();
		this.daysOfWeek = doseSchedule.getDaysOfWeek();
		this.startDate = doseSchedule.getStartDate();
	}
}
