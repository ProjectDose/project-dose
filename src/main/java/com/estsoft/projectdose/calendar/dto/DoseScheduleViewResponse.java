package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
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
	private LocalDate joindate;
	private boolean isDeleted;

	public DoseScheduleViewResponse(DoseSchedule doseSchedule) {
		this.scheduleId = doseSchedule.getScheduleId();
		this.users = doseSchedule.getUsers();
		this.medicationName = doseSchedule.getMedicationName();
		this.doseTime = doseSchedule.getDoseTime();
		this.joindate = doseSchedule.getJoindate();
		this.isDeleted = doseSchedule.isDeleted();
	}
}
