package com.estsoft.projectdose.calendar.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.service.DoseScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

class DoseScheduleControllerTest {
	@Autowired
	private MockMvc mockMvc;  // MockMvc를 사용하여 HTTP 요청을 테스트

	@Mock
	private DoseScheduleService doseScheduleService;  // 서비스는 Mock 객체로 주입

	@InjectMocks
	private DoseScheduleController doseScheduleController;  // Controller 객체를 테스트 대상으로 주입

	@Autowired
	private ObjectMapper objectMapper;  // JSON 변환을 위한 ObjectMapper

	private AddDoseScheduleRequest request;  // 테스트할 요청 객체

	@BeforeEach
	public void setUp() {
		// Mockito 초기화
		MockitoAnnotations.openMocks(this);

		// AddDoseScheduleRequest 객체를 초기화
		request = new AddDoseScheduleRequest(
			123L,
			null, // Users 객체가 필요하다면 이 부분도 적절히 초기화
			"Painkiller",
			Map.of("time1", "08:00", "time2", "12:00", "time3", "18:00"),
			"500mg",
			1,
			Map.of("day1", "mon", "day2", "wed", "day3", "fri"),
			LocalDate.parse("2024-12-01")
		);
	}

	@Test
	void saveDoseSchedule() throws Exception {
		// mock 서비스를 호출하여, saveDoseSchedule 메서드가 정상적으로 호출되도록 설정
		doNothing().when(doseScheduleService).saveDoseSchdule(any(AddDoseScheduleRequest.class));

		// HTTP POST 요청을 보내고 응답을 받음
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/dose-schedule/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))  // 요청 객체를 JSON 문자열로 변환
			.andReturn().getResponse();

		// HTTP 응답 상태가 200 OK인지 확인
		assertEquals(200, response.getStatus());

		// 서비스 메서드가 호출되었는지 검증
		verify(doseScheduleService, times(1)).saveDoseSchdule(any(AddDoseScheduleRequest.class));
	}
}