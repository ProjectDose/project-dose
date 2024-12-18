package com.estsoft.projectdose.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.report.dto.MedicationReportDto;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, Long> {
	//날짜로 찾기 투약 스케쥴 찾기
	List<DoseSchedule> findByStartDate(LocalDate startDate);

	@Query("SELECT new com.estsoft.projectdose.report.dto.MedicationReportDto(" +
		"ds.medicationName, " +
		"COALESCE(ds.repeatInterval, 0), " +
		"ds.dosage, " +
		"ds.repeatInterval, " +
		"ds.daysOfWeek) " +
		"FROM DoseSchedule ds " +
		"WHERE ds.users.id = :userId " +
		"AND (ds.startDate BETWEEN :startDate AND :endDate)")
	List<MedicationReportDto> findMedicationSchedules(
		@Param("userId") Long userId,
		@Param("startDate") LocalDate startDate,
		@Param("endDate") LocalDate endDate
	);
}
