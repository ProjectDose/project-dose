package com.estsoft.projectdose.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estsoft.projectdose.calendar.entity.DoseLog;

public interface DoseLogRepository extends JpaRepository<DoseLog, Long> {
}
