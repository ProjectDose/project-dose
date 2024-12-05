package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDoseScheduleRequest {
	private Long scheduleId;
	private Users users;
	private String medicationName;
	private Map<String,Object> doseTime;
	private LocalDate joindate;
	private boolean isDeleted;

	public DoseSchedule toEntity(){
		return DoseSchedule.builder().id(scheduleId).users(users).medicationName(medicationName).doseTime(doseTime).joindate(joindate).build();
	}
}
