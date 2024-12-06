package com.estsoft.projectdose.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.CalendarRepository;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

@Service
public class CalendarService {
	public CalendarRepository cr;
	public DoseScheduleRepository dsr;
	public CalendarService(CalendarRepository cr) {
		this.cr = cr;
	}
	public List<Calendar> calendarList() throws Exception{
		return cr.findAll();
	}

	public Calendar calendarSave(Calendar calendar){
		return cr.save(calendar);
	}

	public void calendarDelete(Long id) throws Exception{
		cr.deleteById(id);
	}
	public List<DoseSchedule> findAll(){
		return dsr.findAll();
	}
}
