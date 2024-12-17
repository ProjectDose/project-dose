package com.estsoft.projectdose.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, Long> {
	//날짜로 찾기 투약 스케쥴 찾기
	List<DoseSchedule> findByStartDate(LocalDate startDate);
}
