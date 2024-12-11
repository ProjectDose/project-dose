package com.estsoft.projectdose.calendar.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.dto.DoseScheduleResponse;
import com.estsoft.projectdose.calendar.dto.DoseScheduleViewResponse;
import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.service.CalendarService;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;

//달력 틀을 만들고 보여주기 위한 달력 컨트롤러. 스케쥴의 추가와 삭제는 DoseSchduleController에 정의
@RestController
@RequestMapping("/api")
public class CallenderController {
	@Autowired
	private CalendarService calendarService;
	@Autowired
	private DoseScheduleService doseScheduleService;

	@GetMapping("/")
	public String home() {
		return "Calendar";
	}

	//캘린더에 투약 일정 조회
	@GetMapping("/doseschedule")
	public String getDoseSchedules(Model model){
		List<DoseScheduleViewResponse>doseschedule = calendarService.findAll().stream()
													.map(DoseScheduleViewResponse::new)
													.toList();

		model.addAttribute("doseschedule", doseschedule);
		return "Calendar";
	}
	@GetMapping("/dose")
	public ResponseEntity<List<DoseScheduleResponse>> findAllDoseSchedule(){
		List<DoseScheduleResponse>list = calendarService.findAll().stream().map(DoseScheduleResponse::new).toList();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/CalendarList")
	public List<DoseSchedule>calendarList() throws Exception{
		List<DoseSchedule> doseSchedules = doseScheduleService.DoseScheduleList();
			return doseSchedules;
	}
	@PostMapping("/doseschedule")
	public ResponseEntity<DoseSchedule> createDoseSchedule(@RequestBody AddDoseScheduleRequest request) {
		DoseSchedule newDoseSchedule = calendarService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(newDoseSchedule);
	}

	//새로운 일정 추가
	@PostMapping("/dose")
	public Calendar calendarSave(@RequestBody Map<String,Object> date){
		Calendar calendar = new Calendar();
		calendar.setTitle((String) date.get("title"));

		//서울 시간(UTC +8)으로 전환
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		ZonedDateTime startUTC = ZonedDateTime.parse((String)date.get("startdate"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
		ZonedDateTime endUTC = date.get("enddate") != null ? ZonedDateTime.parse((String) date.get("enddate"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul")) : null;

		calendar.setStartdate(startUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		calendar.setEnddate(endUTC != null ? endUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
		calendar.setAllday((Boolean) date.get("allDay"));

		calendarService.calendarSave(calendar);

		return calendar;
	}
	@DeleteMapping("/CalendarDelete")
	public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
		calendarService.calendarDelete(id);
		return ResponseEntity.ok().build();
	}
}
