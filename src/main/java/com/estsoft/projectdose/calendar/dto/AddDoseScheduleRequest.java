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
	private Users user;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseSchedule toEntity(){
		return DoseSchedule.builder()
			.id(scheduleId)
			.users(user)
			.medicationName(medicationName)
			.doseTime(doseTime)
			.dosage(dosage)
			.repeatInterval(repeatInterval)
			.daysOfWeek(daysOfWeek)
			.startDate(startDate)
			.build();
	}
}
