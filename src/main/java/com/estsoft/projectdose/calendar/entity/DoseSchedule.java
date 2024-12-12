package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDate;
import java.util.Map;

import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.estsoft.projectdose.users.entity.Users;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DoseSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_id",nullable = false,unique = true)
	private Long ScheduleId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;

	@Column(name = "medication_name", nullable = false, length = 100)
	private String medicationName;

	@Type(JsonType.class)
	@Column(name = "dose_time", nullable = false, columnDefinition = "json")
	private Map<String,Object> doseTime;

	@CreatedDate
	@Column(name = "join_date", nullable = false)
	private LocalDate joindate;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Column(name = "repeat_interval")
	private Integer repeatInterval;

	@Column(name = "dosage")
	private String dosage;
}
