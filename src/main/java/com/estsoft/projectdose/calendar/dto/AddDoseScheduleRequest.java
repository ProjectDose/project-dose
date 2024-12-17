package com.estsoft.projectdose.calendar.dto;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDoseScheduleRequest {
	private Long scheduleId;
	private Long userId;
	private String medicationName;
	private Map<String,Object> doseTime;
	private String dosage;
	private int repeatInterval;
	private Map<String,Object> daysOfWeek;
	private LocalDate startDate;

	public DoseSchedule toEntity(UsersRepository usersRepository){
		Long userId = getCurrentUserId();

		Users user = usersRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("사용자를 찾을수 없습니다."));

		return DoseSchedule.builder()
			.id(scheduleId)
			.users(user)
			.medicationName(medicationName)
			.doseTime(doseTime)
			.dosage(dosage)
			.repeatInterval(repeatInterval)
			.daysOfWeek(daysOfWeek)
			.startDate(startDate)
			.build();
	}
	private Long getCurrentUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return Long.valueOf(((UserDetails)principal).getUsername());
		} else {
			throw new IllegalStateException("사용자 인증 정보를 찾을 수 없습니다.");
		}
	}
}
