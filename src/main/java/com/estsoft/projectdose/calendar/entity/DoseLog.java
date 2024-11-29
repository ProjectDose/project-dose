package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DoseLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="log_id",nullable = false,unique = true)
	private Long id;

	@ManyToOne
	@Column(name = "schedule_id")
	private DoseSchedule doseSchedule;

	@Column(name = "taken", nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
	private boolean taken = false;

	@Column(name = "taken_time", nullable = false)
	private LocalDateTime takenTime;

	@Column(name = "dose_time", nullable = false)
	private LocalDateTime doseTime;
}
