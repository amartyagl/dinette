package com.menuservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.menuservice.model.TimeTable.Day;
import com.menuservice.schedule.ScheduleResponse;
import com.menuservice.serviceImpl.QuartzSchedulerService;

@WebMvcTest(value = SchedulerController.class)
class SchedulerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	QuartzSchedulerService schedulerService;
	
	@MockBean
	ScheduleResponse response;
	
	@InjectMocks
	SchedulerController schedulerController;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(schedulerController).build();
		response = new ScheduleResponse(true, "Monday", "SchedulingMondayOrder", "Monday Order Scheduled", null);
	}

	@Test
	void addScheduler_success() throws Exception {
		Map<Day, ScheduleResponse> map = new HashMap<>();
		map.put(Day.MONDAY, response);
		when(schedulerService.scheduleOrder("amith@gmail.com")).thenReturn(map);
		mockMvc.perform(MockMvcRequestBuilders.post("/v3/api/scheduler/amith@gmail.com")
				.content(asJsonString(map))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(1)));
	}
	
	@Test
	void deleteSchedule() throws Exception{
		when(schedulerService.deletescheduleOrder("amith@gmail.com")).thenReturn("Cancelled all scheduled order");
		mockMvc.perform(MockMvcRequestBuilders.delete("/v3/api/cancelSchedule/amith@gmail.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$", is("Cancelled all scheduled order")));
				
				
	}
	
	public String asJsonString(final Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter objectWriter = objectMapper.writer();
		try {
			String context = objectWriter.writeValueAsString(obj);
			return context;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
