package com.estsoft.projectdose.calendar.controller;

import static org.hamcrest.MatcherAssert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.estsoft.projectdose.calendar.dto.AddDoseScheduleRequest;
import com.estsoft.projectdose.calendar.entity.DoseSchedule;
import com.estsoft.projectdose.calendar.repository.DoseScheduleRepository;
import com.estsoft.projectdose.users.entity.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CallenderControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private DoseScheduleRepository dsr;

	@BeforeEach
	public void MockMvcSetup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		dsr.deleteAll();
	}
	@DisplayName("캘린더 추가 성공")
	@Test
	void createDoseSchedule() throws Exception {
		//given
		String url = "/doseschedule";
		Long id = 1L;
		Long userId = 1L;
		String medicateionName = "a";
		Map<String,Object> doseTime = null;
		String dosage = "dosage";
		int	repeatInterval = 1;
		Map<String,Object> daysOfWeek = null;
		LocalDate startDate = LocalDate.now();

		AddDoseScheduleRequest request = new AddDoseScheduleRequest
			(id,userId,medicateionName,doseTime,dosage,repeatInterval,daysOfWeek,startDate);

		String json = objectMapper.writeValueAsString(request);

		ResultActions result = mockMvc.perform(post(url)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(json));

		result.andExpect(status().isCreated());

		List<DoseSchedule> list = dsr.findAll();

		assertThat(list.get(0).getDosage()).isEqualTo(dosage);
	}

	@Test
	void findAllDoseSchedule() throws Exception{
		final String url = "/dose";
		final Long id = 1L;
		final Users us
		final String medicateionName = "a";
		final Map<String,Object> doseTime = null;
		final String dosage = "dosage";
		final int	repeatInterval = 1;
		final Map<String,Object> daysOfWeek = null;
		final LocalDate startDate = LocalDate.now();
		DoseSchedule ds = dsr.save(new DoseSchedule(id,users,medicateionName,doseTime,dosage,repeatInterval,daysOfWeek,startDate));

		ResultActions result = mockMvc.perform()
	}
}