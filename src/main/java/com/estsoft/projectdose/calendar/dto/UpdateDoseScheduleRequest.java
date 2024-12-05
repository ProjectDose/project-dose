package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import com.estsoft.projectdose.users.entity.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDoseScheduleRequest {
	private Long scheduleId;
	private Users users;
	private String medicationName;
	private Map<String,Object> doseTime;
	private LocalDate joindate;
	private boolean isDeleted;

	public UpdateDoseScheduleRequest(Long scheduleId,Users users,String medicationName,Map<String,Object> doseTime,LocalDate joindate,boolean isDeleted) {
		this.scheduleId = scheduleId;
		this.users = users;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.joindate = joindate;
		this.isDeleted = isDeleted;
	}
}
