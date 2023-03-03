package com.menuservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.menuservice.dto.TimeTableDto;
import com.menuservice.service.TimeTableService;

@RestController
@RequestMapping(value = "/v2/api")

@RefreshScope
public class TimeTableController {

	@Autowired
	TimeTableService timeTableService;

	/*
	 * This Method is to schedule the food items for a week
	 * @Request Body is used to map the json content to java object
	 */
	@PostMapping(value = "/timeTable")
	public ResponseEntity<TimeTableDto> addTimeTable(@Valid @RequestBody TimeTableDto timeTableDto) {
		return new ResponseEntity<>(timeTableService.addTimeTable(timeTableDto), HttpStatus.ACCEPTED);
	}

	/*
	 * This Method is to get all the food items for a week added by the customer
	 */
	@GetMapping(value = "/timeTable")
	public ResponseEntity<List<TimeTableDto>> getAllTimeTable() {
		return new ResponseEntity<>(timeTableService.getAllTimeTable(), HttpStatus.OK);
	}

	/*
	 * This Method is to get the food items added for a week by using emailId.
	 * @PathVariable is used to fetch the content from the url.
	 */
	@GetMapping(value = "/timeTable/{emailId}")
	public ResponseEntity<TimeTableDto> getTimeTableByEmail(@PathVariable String emailId) {
		return new ResponseEntity<>(timeTableService.getTimeTableByEmail(emailId), HttpStatus.OK);
	}

	/*
	 * This Method is to get the food items added for a week by using Time Table ID.
	 * @PathVariable is used to fetch the content from the url.
	 */
	@GetMapping(value = "/timeTableById/{timetableId}")
	public ResponseEntity<TimeTableDto> getTimeTableById(@PathVariable String timetableId) {
		return new ResponseEntity<>(timeTableService.getTimeTableById(timetableId), HttpStatus.OK);
	}

	/*
	 * This Method is to update the food items added for a week by using Time Table ID.
	 * @PathVariable is used to fetch the content from the url.
	 * @Request Body is used to map the json content to java object
	 */
	@PutMapping(value = "/timeTableById/{timetableId}")
	public ResponseEntity<TimeTableDto> updateTimeTableByID(@RequestBody TimeTableDto timeTableDto,
			@PathVariable String timetableId) {
		System.out.println(timetableId);
		return new ResponseEntity<>(timeTableService.updateTimeTable(timetableId, timeTableDto), HttpStatus.CREATED);
	}

	/*
	 * This Method is to update the food items added for a week by using Email ID.
	 * @PathVariable is used to fetch the content from the url.
	 * @RequestBody is used to map the json content to java object
	 */
	@PutMapping(value = "/timeTableByEmail/{emailId}")
	public ResponseEntity<TimeTableDto> updateTimeTableByEmail(@RequestBody TimeTableDto timeTableDto,
			@PathVariable String emailId) {
		return new ResponseEntity<>(timeTableService.updateTimeTableByEmail(emailId, timeTableDto), HttpStatus.CREATED);
	}

	/*
	 * This Method is to update the food items added for a week by using Time Table Id.
	 * @PathVariable is used to fetch the content from the url.
	 */
	@DeleteMapping(value = "/timeTable/{timetableId}")
	public ResponseEntity<String> deleteMenuById(@PathVariable String timetableId) {
		timeTableService.deleteTimeTableById(timetableId);
		return new ResponseEntity<>("product deleted successfully", HttpStatus.ACCEPTED);
	}

}
