package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDate;
import java.util.Map;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.estsoft.projectdose.users.entity.Users;
import com.vladmihalcea.hibernate.type.json.JsonType;

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
	@Column(name = "user_id")
	private Users users;

	@Column(name = "medication_name", nullable = false, length = 100)
	private String medjcationName;

	@Type(type = "json")
	@Column(name = "dose_time", nullable = false, columnDefinition = "json")
	private Map<String,Object> doseTime;

	@CreatedDate
	@Column(name = "join_date", nullable = false)
	private LocalDate joindate;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted = false;
}
