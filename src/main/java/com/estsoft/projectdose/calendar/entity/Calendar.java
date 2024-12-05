package com.estsoft.projectdose.calendar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Calendar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="calendar_id",nullable = false,unique = true)
	private Long calendarId;
	@Column
	private String title;
	@Column
	private String startdate;
	@Column
	private String enddate;
	@Column
	private Boolean allday;
}
