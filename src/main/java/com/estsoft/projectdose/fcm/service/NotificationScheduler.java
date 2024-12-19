package com.estsoft.projectdose.fcm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.users.service.UsersService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

	private final DoseScheduleRepository doseScheduleRepository;
	private final UsersService userService;
	private final NotificationService notificationService;

	@Scheduled(cron = "0 * * * * *")
	public void sendMedicationReminders() {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

		List<DoseSchedule> schedules = doseScheduleRepository.findActiveSchedules(today);

		for (DoseSchedule schedule : schedules) {
			if (schedule.getDaysOfWeek().containsValue(today.getDayOfWeek().toString()) &&
				schedule.getDoseTime().containsValue(now.toString())) {

				List<String> tokens = userService.getDeviceTokens(schedule.getUsers().getId());
				String title = now.format(DateTimeFormatter.ofPattern("HH:mm")) + "시 입니다";
				String body = "약 먹을 시간이에요";

				for (String token : tokens) {
					notificationService.sendNotification(token, title, body);
				}
			}
		}
	}
}
