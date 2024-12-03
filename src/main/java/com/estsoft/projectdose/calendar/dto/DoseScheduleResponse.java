package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.estsoft.projectdose.users.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
}
