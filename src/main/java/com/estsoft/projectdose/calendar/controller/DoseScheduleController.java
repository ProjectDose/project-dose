package com.estsoft.projectdose.calendar.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.projectdose.calendar.dto.DoseScheduleResponse;
import com.estsoft.projectdose.calendar.dto.DoseScheduleViewResponse;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;

//투약 스케쥴을 달력에 저장/삭제 하기 위한 컨트롤러
@Controller
public class DoseScheduleController {
	private DoseScheduleService doseScheduleService;

	public DoseScheduleController(DoseScheduleService doseScheduleService) {
		this.doseScheduleService = doseScheduleService;
	}

	//투약 스케쥴 보여주기

	//새로운 일정 추가
	@GetMapping("/newDoseSchedule")
	public String newDoseSchedule(@RequestParam(required = false)Long id, Model model) {
		if(id==null){
			model.addAttribute("DoseSchedule", new DoseScheduleViewResponse());
		}else{
			DoseSchedule doseSchedule = doseScheduleService.findByid(id);
			model.addAttribute("DoseSchedule", new DoseScheduleResponse(doseSchedule));
		}
		return "DoseSchedule";
	}

	//투약 스케쥴 삭제
	@DeleteMapping("/DoseScheduleDelete")
	public ResponseEntity<Void> deleteDoseSchedule(@RequestParam Long id){
		doseScheduleService.DoseScheduleDelete(id);
		return ResponseEntity.ok().build();
	}
}
