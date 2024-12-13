package com.estsoft.projectdose.calendar.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class DoseScheduleControllerTest {
	@Autowired
	private MockMvc mockMvc;  // MockMvc를 사용하여 HTTP 요청을 테스트
	@Autowired
	private ObjectMapper objectMapper;  // JSON 변환을 위한 ObjectMapper
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private DoseScheduleRepository doseScheduleRepository;

	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		doseScheduleRepository.deleteAll();
	}

	@DisplayName("데이터 주입")
	@Test
	void generateDoseSchedules() throws Exception {
		//given
		String url = "/api/generate";
		Long scheduleId = 1L;
		Long userId = 1L;
		String medicationName = "a";
		Map<String,Object> doseTime = new HashMap<>();
		doseTime.put("1","08:00");
		doseTime.put("2","13:00");
		doseTime.put("3","18:00");
		String dosage = "500mg";
		int repeatInterval = 3;
		Map<String, Object>daysOfWeek = new HashMap<>();
		daysOfWeek.put("1","Monday");
		LocalDate startDate = LocalDate.parse("2024-12-13");
		AddDoseScheduleRequest request = new AddDoseScheduleRequest(scheduleId,userId,medicationName,doseTime,dosage,repeatInterval,daysOfWeek,startDate);

		String requestBody = objectMapper.writeValueAsString(request);

		//when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(requestBody));

		//then
		result.andExpect(status().isCreated());
		List<DoseSchedule> doseScheduleList = doseScheduleRepository.findAll();

		assertThat(doseScheduleList.size()).isEqualTo(1);
		assertThat(doseScheduleList.get(0).getMedicationName()).isEqualTo(medicationName);
		assertThat(doseScheduleList.get(0).getDosage()).isEqualTo(dosage);
		assertThat(doseScheduleList.get(0).getRepeatInterval()).isEqualTo(repeatInterval);
		assertThat(doseScheduleList.get(0).getStartDate()).isEqualTo(startDate);
		assertThat(doseScheduleList.get(0).getScheduleId()).isEqualTo(scheduleId);
	}
}