package com.estsoft.projectdose.fcm.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.fcm.dto.FcmSendDto;
import com.estsoft.projectdose.fcm.service.FcmService;
import com.estsoft.projectdose.users.entity.DeviceToken;
import com.estsoft.projectdose.users.repository.DeviceTokenRepository;

@Service
public class NotificationScheduler {

	private final DoseScheduleRepository doseScheduleRepository;
	private final DeviceTokenRepository deviceTokenRepository;
	private final FcmService fcmService;

	@Autowired
	public NotificationScheduler(DoseScheduleRepository doseScheduleRepository,
		DeviceTokenRepository deviceTokenRepository,
		FcmService fcmService) {
		this.doseScheduleRepository = doseScheduleRepository;
		this.deviceTokenRepository = deviceTokenRepository;
		this.fcmService = fcmService;
	}

	@Scheduled(cron = "0 * * * * *")  // Runs every minute
	public void sendDoseNotifications() {
		LocalDate today = LocalDate.now();
		LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);

		List<DoseSchedule> schedules = doseScheduleRepository.findActiveSchedules(today);

		Map<Long, List<DoseSchedule>> userSchedules = schedules.stream()
			.filter(schedule -> isScheduledForToday(today, schedule.getStartDate(), schedule.getRepeatInterval(), schedule.getDaysOfWeek())
				&& isDoseTimeNow(currentTime, schedule.getDoseTime()))
			.collect(Collectors.groupingBy(schedule -> schedule.getUsers().getId()));

		userSchedules.forEach((userId, userDoseSchedules) -> {
			List<DeviceToken> tokens = deviceTokenRepository.findAllByUser_Id(userId);

			String notificationTime = currentTime.toString();

			tokens.forEach(token -> sendPushNotification(token.getToken(), notificationTime));
		});
	}

	private boolean isScheduledForToday(LocalDate today, LocalDate startDate, Integer interval, Map<String, Object> daysOfWeek) {
		DayOfWeek todayDay = today.getDayOfWeek();
		boolean isCorrectDay = daysOfWeek.values().contains(todayDay.toString().toUpperCase());
		boolean isCorrectInterval = interval == null || interval == 0 || today.isEqual(startDate) || (!today.isBefore(startDate) && (today.toEpochDay() - startDate.toEpochDay()) % interval == 0);
		return isCorrectDay && isCorrectInterval;
	}

	private boolean isDoseTimeNow(LocalTime currentTime, Map<String, Object> doseTimes) {
		return doseTimes.values().stream()
			.map(time -> LocalTime.parse((String) time))
			.anyMatch(time -> time.equals(currentTime));
	}

	private void sendPushNotification(String token, String notificationTime) {
		FcmSendDto notification = FcmSendDto.builder()
			.token(token)
			.title("삐약")
			.body(notificationTime + "분 입니다 약 먹을 시간이에요")
			.build();

		try {
			fcmService.sendMessageTo(notification);
		} catch (Exception e) {
			System.err.println("Error sending notification: " + e.getMessage());
		}
	}
}

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import com.estsoft.projectdose.calendar.entity.DoseSchedule;
// import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
// import com.estsoft.projectdose.users.service.UsersService;
//
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.time.temporal.ChronoUnit;
// import java.util.List;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class NotificationScheduler {
//
// 	private final DoseScheduleRepository doseScheduleRepository;
// 	private final UsersService userService;
// 	private final NotificationService notificationService;
//
// 	@Scheduled(cron = "0 * * * * *")
// 	public void sendMedicationReminders() {
// 		LocalDate today = LocalDate.now();
// 		LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
//
// 		List<DoseSchedule> schedules = doseScheduleRepository.findActiveSchedules(today);
//
// 		for (DoseSchedule schedule : schedules) {
// 			if (schedule.getDaysOfWeek().containsValue(today.getDayOfWeek().toString()) &&
// 				schedule.getDoseTime().containsValue(now.toString())) {
//
// 				List<String> tokens = userService.getDeviceTokens(schedule.getUsers().getId());
// 				String title = now.format(DateTimeFormatter.ofPattern("HH:mm")) + "시 입니다";
// 				String body = "약 먹을 시간이에요";
//
// 				for (String token : tokens) {
// 					notificationService.sendNotification(token, title, body);
// 				}
// 			}
// 		}
// 	}
// }
