package com.estsoft.projectdose.calendar.service;

import java.util.List;
import java.util.Map;

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
	public List<DoseSchedule> DoseScheduleList() throws Exception{
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
