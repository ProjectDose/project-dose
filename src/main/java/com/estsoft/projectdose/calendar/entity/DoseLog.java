package com.estsoft.projectdose.calendar.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DoseLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="log_id",nullable = false,unique = true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private DoseSchedule doseSchedule;

	@Column(name = "taken", nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
	private boolean taken;

	@Column(name = "taken_time", nullable = false)
	private LocalDateTime takenTime;

	@Column(name = "dose_time", nullable = false)
	private LocalTime doseTime;
}
