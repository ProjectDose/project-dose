package com.estsoft.projectdose.calendar.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.dto.UpdateDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

@Service
public class DoseScheduleService {
	public final DoseScheduleRepository doseScheduleRepository;

	public DoseScheduleService(DoseScheduleRepository doseschedulerepository) {
		this.doseScheduleRepository = doseschedulerepository;
	}
	//입력 받은 데이터를 Json 데이터들과 RepeatInterval에 따라서 input될 data형식들을 추가
	public List<DoseSchedule> generateSchedule(DoseSchedule doseSchedule) {
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
		Set<DayOfWeek> targetDays = daysOfWeek.values().stream()
			.map(day -> DayOfWeek.valueOf(((String) day).toUpperCase()))
			.collect(Collectors.toSet());

		LocalDate currentDate = startDate;
		int daysProcessed = 0;

		while (daysProcessed < repeatInterval) {
			if (daysOfWeek.isEmpty() || targetDays.contains(currentDate.getDayOfWeek())) {
				for (LocalTime time : doseTimes) {
					DoseSchedule newSchedule = new DoseSchedule();
					newSchedule.setMedicationName(doseSchedule.getMedicationName());
					newSchedule.setUsers(doseSchedule.getUsers());
					newSchedule.setDosage(doseSchedule.getDosage());
					newSchedule.setStartDate(currentDate);
					newSchedule.setDoseTime(Map.of("time", time.toString()));
					newSchedule.setDaysOfWeek(Map.of("day", currentDate.getDayOfWeek().toString()));
					newSchedule.setRepeatInterval(0); // 개별 일정은 반복 정보가 필요 없음
					schedules.add(newSchedule);
				}
			}
			currentDate = currentDate.plusDays(1);
			daysProcessed++;
		}
		return schedules;
	}
	public List<DoseSchedule> findAll(){
		return doseScheduleRepository.findAll();
	}
	public void DoseScheduleDelete(Long id){
		doseScheduleRepository.deleteById(id);
	}
	public DoseSchedule update(Long id,UpdateDoseScheduleRequest request){
		DoseSchedule doseSchedule = doseScheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("no such dose schedule :" + id));

		doseSchedule.update(request.getScheduleId(), request.getMedicationName(), request.getDoseTime(),
					request.getDosage(), request.getStartDate(), request.getRepeatInterval(), request.getDaysOfWeek());
		return doseSchedule;
	}

	public DoseSchedule findByid(Long id) {
		return doseScheduleRepository.findById(id).orElse(null);
	}

	public void saveDoseSchdule(AddDoseScheduleRequest request) {
		validateDoseTime(request.getDoseTime());
		DoseSchedule schedule = request.toEntity();
		doseScheduleRepository.save(schedule);
	}

	//입력 시간 형식 검증
	public void validateDoseTime(Map<String, Object> doseTime){
		for(Map.Entry<String, Object> entry : doseTime.entrySet()){
			String time = (String)entry.getValue();
			if(!time.matches("^([01]\\d|2[0-3]):[0-5]\\d$")){
				throw new IllegalArgumentException("시간은 HH : MM 형태로 입력해 주세요");
			}
		}
	}

}
