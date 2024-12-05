package com.estsoft.projectdose.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.estsoft.projectdose.calendar.entity.DoseLog;
import com.estsoft.projectdose.report.dto.LogDetails;

@Repository
public interface DoseLogRepository extends JpaRepository<DoseLog, Long> {
	@Query("""
		    SELECT dl.takenTime, AVG(CASE WHEN dl.taken = true THEN 100.0 ELSE 0.0 END) as achievementRate
		    FROM DoseLog dl JOIN dl.doseSchedule ds
		    WHERE ds.users.id = :userId
		    GROUP BY dl.takenTime
		""")
	List<Object[]> findMonthlyAchievementRates(@Param("userId") Long userId);


	@Query("""
        SELECT AVG(CASE WHEN dl.taken = true THEN 100.0 ELSE 0.0 END) as achievementRate
        FROM DoseLog dl JOIN dl.doseSchedule ds
        WHERE ds.users.id = :userId AND dl.takenTime = :selectedDate
    """)
	Double calculateDailyAchievementRate(
		@Param("userId") Long userId,
		@Param("selectedDate") LocalDate selectedDate
	);


	@Query("""
        SELECT new com.estsoft.projectdose.report.dto.LogDetails(
            ds.medicationName,
            dl.doseTime,
            dl.taken
        )
        FROM DoseLog dl JOIN dl.doseSchedule ds
        WHERE ds.users.id = :userId AND dl.takenTime = :selectedDate
    """)
	List<LogDetails> findDailyDoseLogs(
		@Param("userId") Long userId,
		@Param("selectedDate") LocalDate selectedDate
	);
}
