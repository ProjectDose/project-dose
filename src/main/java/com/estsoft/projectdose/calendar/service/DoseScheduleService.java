package com.estsoft.projectdose.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.dto.UpdateDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

@Service
public class DoseScheduleService {
	public final DoseScheduleRepository dsr;

	public DoseScheduleService(DoseScheduleRepository doseschedulerepository) {
		this.dsr = doseschedulerepository;
	}
	public List<DoseSchedule> DoseScheduleList() throws Exception{
		return dsr.findAll();
	}
	public DoseSchedule DoseScheduleSave(AddDoseScheduleRequest request){
		return dsr.save(request.toEntity());
	}
	public void DoseScheduleDelete(Long id){
		dsr.deleteById(id);
	}
	public DoseSchedule update(Long id, UpdateDoseScheduleRequest request){
		DoseSchedule doseSchedule = dsr.findById(id).orElseThrow(()-> new IllegalArgumentException("no such dose schedule name" + id));

		doseSchedule.update(request.getScheduleId(),request.getUsers(),request.getMedicationName(),request.getDoseTime(),request.getJoindate(),request.isDeleted());
		return doseSchedule;
	}

	public DoseSchedule findById(Long id) {
		return dsr.deleteById(id);
	}
}
