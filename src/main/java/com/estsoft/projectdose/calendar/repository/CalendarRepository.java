package com.estsoft.projectdose.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
