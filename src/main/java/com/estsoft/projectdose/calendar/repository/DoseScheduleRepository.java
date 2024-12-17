package com.estsoft.projectdose.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.report.dto.MedicationReportDto;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, Long> {
	@Query("SELECT new com.estsoft.projectdose.report.dto.MedicationReportDto(" +
		"ds.medicationName, " +
		"ds.repeatInterval, " +
		"ds.dosage, " +
		"CASE WHEN ds.repeatInterval > 0 THEN ds.repeatInterval ELSE null END, " +
		"ds.daysOfWeek) " +
		"FROM DoseSchedule ds " +
		"WHERE ds.users.id = :userId " +
		"AND (DATE(ds.startDate) BETWEEN :startDate AND :endDate)")
	List<MedicationReportDto> findMedicationSchedules(
		@Param("userId") Long userId,
		@Param("startDate") LocalDate startDate,
		@Param("endDate") LocalDate endDate
	);
}
