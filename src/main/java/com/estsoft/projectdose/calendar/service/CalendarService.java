package com.estsoft.projectdose.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.CalendarRepository;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

@Service
public class CalendarService {
	public CalendarRepository calendarRepository;
	public DoseScheduleRepository doseScheduleRepository;
	public CalendarService(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}
	public List<Calendar> calendarList() throws Exception{
		return calendarRepository.findAll();
	}

	public Calendar calendarSave(Calendar calendar){
		return calendarRepository.save(calendar);
	}

	public void calendarDelete(Long id){
		calendarRepository.deleteById(id);
	}
	public List<DoseSchedule> findAll(){
		return doseScheduleRepository.findAll();
	}
}
