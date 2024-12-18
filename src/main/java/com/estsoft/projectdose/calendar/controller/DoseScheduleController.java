package com.estsoft.projectdose.calendar.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.estsoft.projectdose.calendar.dto.UpdateDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;
import com.estsoft.projectdose.users.service.UsersService;
import com.google.common.annotations.GwtCompatible;

//투약 스케쥴을 달력에 저장/삭제 하기 위한 컨트롤러
@RestController
public class DoseScheduleController {
	private final DoseScheduleService doseScheduleService;
	private final UsersService usersService;

	public DoseScheduleController(DoseScheduleService doseScheduleService,UsersService usersService) {
		this.doseScheduleService = doseScheduleService;
		this.usersService = usersService;
	}

	//투약 스케쥴 보여주기, 시큐리티 추가되면 세션 로그인 id로 찾도록 변경
	@GetMapping("/api/doseschedule")
	public String getDoseSchedules(Model model){
		List<DoseScheduleViewResponse> doseschedule = doseScheduleService.findAll().stream()
			.map(DoseScheduleViewResponse::new)
			.toList();

		model.addAttribute("doseschedule", doseschedule);
		return "Calendar";
	}

	//스케쥴 생성
	@PostMapping("/api/generate")
	public List<DoseSchedule> generateSchedules(@RequestBody DoseSchedule doseSchedule) {
		return doseScheduleService.generateSchedule(doseSchedule);
	}

	//투약 스케쥴 삭제
	@DeleteMapping("/api/doseschedule/delete/{id}")
	public ResponseEntity<Void> deleteDoseSchedule(@PathVariable Long id){
		doseScheduleService.DoseScheduleDelete(id);
		return ResponseEntity.ok().build();
	}

	//특정날짜 선택시 스케쥴 조회
	@GetMapping("/api/Schedules/{startDate}")
	public ResponseEntity<List<DoseScheduleResponse>> getSchedulesByDate(@PathVariable("startDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate){
		Long userId = usersService.getLoggedInUserId();
		List<DoseScheduleResponse> schedules = doseScheduleService.findSchedulesByDateAndUserId(startDate,userId);
		return ResponseEntity.ok(schedules);
	}
	//투약 스케줄 수정
	@PutMapping("/api/doseschedule/update/{id}")
	public ResponseEntity<DoseScheduleResponse> updateDoseSchedule(
		@PathVariable("id") Long scheduleId,
		@RequestBody UpdateDoseScheduleRequest updateRequest) {

		DoseSchedule updatedSchedule = doseScheduleService.update(scheduleId, updateRequest);

		if (updatedSchedule != null) {
			DoseScheduleResponse response = new DoseScheduleResponse(updatedSchedule);
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
