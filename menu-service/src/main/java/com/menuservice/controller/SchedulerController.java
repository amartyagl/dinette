package com.menuservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.menuservice.model.TimeTable.Day;
import com.menuservice.schedule.ScheduleResponse;
import com.menuservice.serviceImpl.QuartzSchedulerService;

@RestController
@RefreshScope
@RequestMapping(value = "v3/api")
public class SchedulerController {
	
	@Autowired
	private QuartzSchedulerService schedulerService; 
	
	/*
	 * This Method is to create a Schedule to auto order the food
	 */
	@PostMapping(value = "/scheduler/{emailId}")
	public ResponseEntity<Map<Day, ScheduleResponse>> addScheduler(@PathVariable String emailId){
		Map<Day, ScheduleResponse> scheduleOrder = schedulerService.scheduleOrder(emailId);
		return new ResponseEntity<>(scheduleOrder,HttpStatus.CREATED);
	}
	
	/*
	 * This Method is to cancel the Schedule using emailId
	 */
	@DeleteMapping(value = "/cancelSchedule/{emailId}")
	public ResponseEntity<String> deleteScheduler(@PathVariable String emailId){
		String deletescheduleOrder = schedulerService.deletescheduleOrder(emailId);
		return new ResponseEntity<>(deletescheduleOrder,HttpStatus.ACCEPTED);
	}
	
}
