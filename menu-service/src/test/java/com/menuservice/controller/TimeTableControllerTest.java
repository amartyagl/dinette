package com.menuservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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
import com.menuservice.dto.TimeTableDto;
import com.menuservice.serviceImpl.TimeTableServiceImpl;

@WebMvcTest(value = TimeTableController.class)
class TimeTableControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	TimeTableServiceImpl timeTableServiceImpl;

	@MockBean
	TimeTableDto timeTableDto;

	@InjectMocks
	TimeTableController timeTableController;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(timeTableController).build();
		timeTableDto = new TimeTableDto("1", "amith@gmail.com", "amith", "patel", null);
	}

	@Test
	void addTimeTable_success() throws Exception {
		when(timeTableServiceImpl.addTimeTable(timeTableDto)).thenReturn(timeTableDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/v2/api/timeTable").content(asJsonString(timeTableDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId", is("amith@gmail.com")));
	}

	@Test
	void getAllTimeTable_success() throws Exception {
		List<TimeTableDto> alltimeTableDto = Arrays.asList(timeTableDto);
		when(timeTableServiceImpl.getAllTimeTable()).thenReturn(alltimeTableDto);
		mockMvc.perform(MockMvcRequestBuilders.get("/v2/api/timeTable").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(1)));
	}

	@Test
	void getTimeTableByEmail_success() throws Exception {
		when(timeTableServiceImpl.getTimeTableByEmail(timeTableDto.getEmailId())).thenReturn(timeTableDto);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v2/api/timeTable/amith@gmail.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("amith")));
	}

	@Test
	void getTimeTableById_success() throws Exception {
		when(timeTableServiceImpl.getTimeTableById(timeTableDto.getTimetableId())).thenReturn(timeTableDto);
		mockMvc.perform(MockMvcRequestBuilders.get("/v2/api/timeTableById/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("amith")));
	}

	@Test
	void updateTimeTableById_success() throws Exception {
		TimeTableDto updatedTable = new TimeTableDto("1", "amithpatel@gmail.com", "amith", "patel", null);
		when(timeTableServiceImpl.updateTimeTable("1", updatedTable)).thenReturn(updatedTable);
		mockMvc.perform(MockMvcRequestBuilders.put("/v2/api/timeTableById/1").content(asJsonString(updatedTable))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId", is("amithpatel@gmail.com")));
	}

	@Test
	void updateTimeTableByEmailId_success() throws Exception {
		TimeTableDto updatedTable = new TimeTableDto("1", "amithpatel@gmail.com", "amith", "patel", null);
		when(timeTableServiceImpl.updateTimeTableByEmail("amith@gmail.com", updatedTable)).thenReturn(updatedTable);
		mockMvc.perform(MockMvcRequestBuilders.put("/v2/api/timeTableByEmail/amith@gmail.com")
				.content(asJsonString(updatedTable)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId", is("amithpatel@gmail.com")));
	}

	@Test
	void deleteTimeTable() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v2/api/timeTable/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
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
