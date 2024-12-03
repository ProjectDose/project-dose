package com.estsoft.projectdose.calendar.service;

import java.util.List;

import com.estsoft.projectdose.calendar.dto.DoseScheduleResponse;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;

public class DoseScheduleService {
	public DoseScheduleRepository doseschedulerepository;

	public List<DoseScheduleResponse> findAll() {
		List<DoseSchedule> list = doseschedulerepository.findAll();
		return list.stream().map(DoseScheduleResponse::new).tolist();
	}
}
