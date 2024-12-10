package com.estsoft.projectdose.calendar.service;

import java.util.List;

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
	public DoseSchedule DoseScheduleSave(AddDoseScheduleRequest request){
		return doseScheduleRepository.save(request.toEntity());
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
}
