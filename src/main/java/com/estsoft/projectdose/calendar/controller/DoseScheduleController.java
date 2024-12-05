package com.estsoft.projectdose.calendar.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.dto.DoseScheduleResponse;
import com.estsoft.projectdose.calendar.dto.DoseScheduleViewResponse;
import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.service.CalendarService;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;

//투약 스케쥴을 달력에 저장/삭제 하기 위한 컨트롤러
@RestController
public class DoseScheduleController {
	private DoseScheduleService dss;
	private CalendarService cs;

	public DoseScheduleController(DoseScheduleService dss) {
		this.dss = dss;
	}

	//새로운 일정 추가
	@GetMapping("/new-DoseSchedule")
	public String newDoseSchedule(@RequestParam(required = false)Long id, Model model) {
		if(id==null){
			model.addAttribute("DoseSchedule", new DoseScheduleViewResponse());
		}else{
			DoseSchedule doseSchedule = dss.findById(id);
			model.addAttribute("DoseSchedule", new DoseScheduleResponse(doseSchedule));
		}
		return "new-DoseSchedule";
	}

	//투약 스케쥴 삭제
	@DeleteMapping("/DoseScheduleDelete")
	public ResponseEntity<Void> deleteDoseSchedule(@RequestParam Long id){
		dss.DoseScheduleDelete(id);
		return ResponseEntity.ok().build();
	}

	//투약 스케줄 업데이트
	@GetMapping("/DoseSchedule?id={id}")
	public String DoseScheduleUpdate(@PathVariable Long id, @RequestBody Map<String, Object> map){
		DoseSchedule schedule = new DoseSchedule();
		schedule.setScheduleId(id);
		schedule.setMedicationName((String)map.get("medicateionName"));
		schedule.setDoseTime((Map<String, Object>)map.get("dosetime"));
		schedule.setDeleted((Boolean) map.get("isDeleted"));
		try {
			dss.update(schedule);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
