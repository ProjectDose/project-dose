package com.estsoft.projectdose.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, Long> {
}
