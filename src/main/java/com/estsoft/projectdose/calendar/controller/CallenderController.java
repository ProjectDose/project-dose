package com.estsoft.projectdose.calendar.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estsoft.projectdose.calendar.dto.DoseScheduleViewResponse;
import com.estsoft.projectdose.calendar.entity.Calendar;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.service.CalendarService;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;

//달력 틀을 만들고 보여주기 위한 달력 컨트롤러. 스케쥴의 추가와 삭제는 DoseSchduleController에 정의
@RestController
public class CallenderController {
	@Autowired
	private CalendarService cs;
	@Autowired
	private DoseScheduleService dss;

	//캘린더에 투약 일정 조회
	@GetMapping("/DoseSchedules")
	public String getDoseSchedules(Model model){
		List<DoseScheduleViewResponse>doseschedule = cs.findAll().stream().map(DoseScheduleViewResponse::new).toList();

		model.addAttribute("doseschedule", doseschedule);
		return "Calendar";
	}

	@RequestMapping("/CalendarList")
	public List<DoseSchedule>calendarList() throws Exception{
		List<DoseSchedule> doseSchedules = dss.DoseScheduleList();
			return doseSchedules;
	}
	//새로운 일정 추가
	@PostMapping
	public Calendar calendarSave(@RequestBody Map<String,Object> map) throws Exception{
		Calendar calendar = new Calendar();
		calendar.setTitle((String) map.get("title"));

		//서울 시간(UTC +8)으로 전환
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		ZonedDateTime startUTC = ZonedDateTime.parse((String)map.get("startdate"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
		ZonedDateTime endUTC = map.get("enddate") != null ? ZonedDateTime.parse((String) map.get("enddate"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul")) : null;

		calendar.setStartdate(startUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		calendar.setEnddate(endUTC != null ? endUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
		calendar.setAllday((Boolean) map.get("allDay"));

		calendarservice.calendarSave(calendar);

		return calendar;
	}
	@DeleteMapping("/CalendarDelete")
	public String calendarDelete(@RequestParam Long id) throws Exception{
		try{
			calendarservice.calendarDelete(id);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
}
