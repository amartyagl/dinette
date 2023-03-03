package com.menuservice.service;

import java.util.List;

import com.menuservice.dto.TimeTableDto;


public interface TimeTableService {
	
	TimeTableDto addTimeTable(TimeTableDto timeTableDto);
	List<TimeTableDto> getAllTimeTable();
	TimeTableDto updateTimeTable(String timetableId, TimeTableDto timeTableDto);
	void deleteTimeTableById(String timetableId);
	TimeTableDto getTimeTableByEmail(String emailId);
	TimeTableDto getTimeTableById(String timetableId);
	TimeTableDto updateTimeTableByEmail(String emailId, TimeTableDto timeTableDto);

}
