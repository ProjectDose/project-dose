package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoseScheduleResponse {
	private Long ScheduleId;
	private Users users;
	private String medicationName;
	private Map<String,Object> doseTime;
	private LocalDate joindate;
	private boolean isDeleted;

	public DoseScheduleResponse(DoseSchedule doseSchedule) {
		this.ScheduleId = doseSchedule.getScheduleId();
		this.users = doseSchedule.getUsers();
		this.medicationName = doseSchedule.getMedicationName();
		this.doseTime = doseSchedule.getDoseTime();
		this.joindate = doseSchedule.getJoindate();
		this.isDeleted = doseSchedule.isDeleted();
	}
}
