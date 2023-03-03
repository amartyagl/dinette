package com.menuservice.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Document(collection = "ScheduleInfo")
public class ScheduledInfo {

	
	private String emailId;
	
	@Id
	private String jobKey;
	
	private String jobvalue;
	
	private List<Menu> menuItems;
	
	private String day;
	
	
}