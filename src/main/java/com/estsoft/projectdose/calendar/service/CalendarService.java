package com.estsoft.projectdose.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.CalendarRepository;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

@Service
public class CalendarService {
	public final CalendarRepository calendarRepository;
	public final DoseScheduleRepository doseScheduleRepository;

	public CalendarService(CalendarRepository calendarRepository, DoseScheduleRepository doseScheduleRepository) {
		this.calendarRepository = calendarRepository;
		this.doseScheduleRepository = doseScheduleRepository;
	}

	public DoseSchedule save(AddDoseScheduleRequest request) {
		return doseScheduleRepository.save(request.toEntity());
	}

	public List<DoseSchedule> findAll() {
		return doseScheduleRepository.findAll();
	}

	public Calendar calendarSave(Calendar calendar){
		return calendarRepository.save(calendar);
	}

	public void calendarDelete(Long id){
		calendarRepository.deleteById(id);
	}
}
