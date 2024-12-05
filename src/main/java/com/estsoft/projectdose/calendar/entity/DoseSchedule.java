package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDate;
import java.util.Map;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

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
	@Column(name = "user_id")
	private Users users;

	@Column(name = "medication_name", nullable = false, length = 100)
	private String medicationName;

	@Type(type = "json")
	@Column(name = "dose_time", nullable = false, columnDefinition = "json")
	private Map<String,Object> doseTime;

	@CreatedDate
	@Column(name = "join_date", nullable = false)
	private LocalDate joindate;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@Builder
	public DoseSchedule(Long id, Users users, String medicationName, Map<String,Object> doseTime, LocalDate joindate, boolean isDeleted) {
		this.scheduleId = id;
		this.users = users;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.joindate = joindate;
		this.isDeleted = isDeleted;
	}
	public void update(Long id, Users users, String medicationName, Map<String,Object> doseTime, LocalDate joindate, boolean isDeleted){
		this.scheduleId = id;
		this.users = users;
		this.medicationName = medicationName;
		this.doseTime = doseTime;
		this.joindate = joindate;
		this.isDeleted = isDeleted;
	}
}
