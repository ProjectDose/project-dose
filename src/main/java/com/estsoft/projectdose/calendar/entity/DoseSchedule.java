package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;

import com.estsoft.projectdose.users.entity.Users;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DoseSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_id",nullable = false,unique = true)
	private Long scheduleId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private Users users;

	@Column(name = "medication_name", nullable = false, length = 100)
	private String medicationName;

	@Type(JsonType.class)
	@Column(name = "dose_time", nullable = false, columnDefinition = "json")
	private Map<String,Object> doseTime;

	@Column(name = "dosage", length = 100)
	private String dosage;

	@Column(name = "repeat_interval")
	private int repeatInterval;

	@Type(JsonType.class)
	@Column(name = "days_of_week",nullable = false, columnDefinition = "json")
	private Map<String,Object> daysOfWeek;

	@Column(name = "start_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
	private LocalDate startDate;

	@Builder
	public DoseSchedule(Long id, Users users, String medicationName, Map<String,Object> doseTime,String dosage, int repeatInterval, Map<String,Object> daysOfWeek,LocalDate startDate) {
		this.scheduleId = id;
		this.users = users;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.dosage = dosage;
		this.repeatInterval = repeatInterval;
		this.daysOfWeek = daysOfWeek;
		this.startDate = startDate;
	}
	public void update(Long id, String medicationName, Map<String,Object> doseTime,String dosage, LocalDate startDate, int repeatInterval, Map<String,Object> daysOfWeek){
		this.scheduleId = id;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.dosage = dosage;
		this.repeatInterval = repeatInterval;
		this.daysOfWeek = daysOfWeek;
		this.startDate = startDate;
	}

}
