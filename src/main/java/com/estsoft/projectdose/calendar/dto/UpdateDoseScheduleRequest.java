package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.cglib.core.Local;

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
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public UpdateDoseScheduleRequest(Long scheduleId,String medicationName,Map<String,Object> doseTime,String dosage,int repeatInterval,Map<String,Object> daysOfWeek,
		LocalDate startDate) {
		this.scheduleId = scheduleId;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.dosage = dosage;
		this.repeatInterval = repeatInterval;
		this.daysOfWeek = daysOfWeek;
		this.startDate = startDate;
	}

	public Optional<Object> findById(Long id) {
		return Optional.ofNullable(doseTime.get(id.toString()));
	}
}
