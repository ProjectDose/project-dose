package com.estsoft.projectdose.calendar.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.dto.DoseScheduleResponse;
import com.estsoft.projectdose.calendar.dto.UpdateDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.users.entity.Users;
import com.estsoft.projectdose.users.repository.UsersRepository;
import com.estsoft.projectdose.users.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DoseScheduleService {
	private final DoseScheduleRepository doseScheduleRepository;
	private final UsersRepository usersRepository;
	private final UsersService usersService;
	@Autowired
	public DoseScheduleService(DoseScheduleRepository doseschedulerepository, UsersRepository usersRepository,
		UsersService usersService) {
		this.doseScheduleRepository = doseschedulerepository;
		this.usersRepository = usersRepository;
		this.usersService = usersService;
	}
	//입력 받은 데이터를 Json 데이터들과 RepeatInterval에 따라서 input될 data형식들을 추가
	public List<DoseSchedule> generateSchedule(DoseSchedule doseSchedule) {
		Long userId = usersService.getLoggedInUserId(); // 로그인된 사용자의 userId 가져오기
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
		doseSchedule.setUsers(user); // 사용자 설정
		List<DoseSchedule> schedules = new ArrayList<>();

		LocalDate startDate = doseSchedule.getStartDate();
		int repeatInterval = doseSchedule.getRepeatInterval();
		Map<String, Object> doseTime = doseSchedule.getDoseTime();
		Map<String, Object> daysOfWeek = doseSchedule.getDaysOfWeek();

		// 투약 시간 처리
		List<LocalTime> doseTimes = doseTime.values().stream()
			.map(time -> LocalTime.parse((String) time)) // "08:00", "12:00" 형태로 가정
			.sorted()
			.toList();

		// 요일 처리
		List<DayOfWeek> targetDays = daysOfWeek.values().stream()
			.map(day -> DayOfWeek.valueOf(((String) day).toUpperCase()))
			.sorted()
			.toList();

		// 반복 주기에 따른 스케줄 생성
		for (int cycle = 0; cycle < repeatInterval; cycle++) {
			for (DayOfWeek day : targetDays) {
				LocalDate targetDate = startDate.plusWeeks(cycle).with(TemporalAdjusters.nextOrSame(day));

				for (LocalTime time : doseTimes) {
					DoseSchedule newSchedule = new DoseSchedule();
					newSchedule.setMedicationName(doseSchedule.getMedicationName());
					newSchedule.setUsers(doseSchedule.getUsers());
					newSchedule.setDosage(doseSchedule.getDosage());
					newSchedule.setStartDate(targetDate);
					newSchedule.setDoseTime(Map.of("time", time.toString()));
					newSchedule.setDaysOfWeek(Map.of("day", targetDate.getDayOfWeek().toString()));
					newSchedule.setRepeatInterval(0); // 개별 일정은 반복 정보가 필요 없음
					schedules.add(newSchedule);
				}
			}
		}
		return doseScheduleRepository.saveAll(schedules);
	}
	public List<DoseSchedule> findAll(){
		return doseScheduleRepository.findAll();
	}
	public void DoseScheduleDelete(Long id){
		doseScheduleRepository.deleteById(id);
	}
	public DoseSchedule update(Long id,UpdateDoseScheduleRequest request){
		DoseSchedule doseSchedule = doseScheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 스케쥴이 없습니다 ."));

		ObjectMapper objectMapper = new ObjectMapper();
		// 수정할 데이터가 있으면 업데이트
		if (request.getDoseTime() != null) {
			try{
				String doseTimeStr = request.getDoseTime();
				String wrappedJson = String.format("{\"time\": \"%s\"}", doseTimeStr);
				Map<String, Object> doseTimeMap = objectMapper.readValue(wrappedJson, new TypeReference<>() {});
				doseSchedule.setDoseTime(doseTimeMap);
			}catch (JsonProcessingException e){
				throw new IllegalArgumentException("옳지 않은 형식입니다.",e);
			}
		}
		if (request.getMedicationName() != null) {
			doseSchedule.setMedicationName(request.getMedicationName());
		}
		if (request.getDosage() != null) {
			doseSchedule.setDosage(request.getDosage());
		}

		// 데이터 저장
		return doseScheduleRepository.save(doseSchedule);
	}
	//startDate로 조회하는 로직
	public List<DoseScheduleResponse> findSchedulesByDateAndUserId(LocalDate date, Long userId){
		List<DoseSchedule> schedules = doseScheduleRepository.findByStartDateAndUsers_Id(date, userId);
		return schedules.stream().map(DoseScheduleResponse::fromEntity).collect(Collectors.toList());
	}
}
