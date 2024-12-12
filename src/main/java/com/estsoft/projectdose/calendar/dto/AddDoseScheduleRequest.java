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
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseSchedule toEntity(){
		return DoseSchedule.builder()
			.id(scheduleId)
			.users(users)//이후 UserService업데이트 되면 세션의 id받아올 수 있게 수정 필요
			.medicationName(medicationName)
			.doseTime(doseTime)
			.dosage(dosage)
			.repeatInterval(repeatInterval)
			.daysOfWeek(daysOfWeek)
			.startDate(startDate)
			.build();
	}
}
