package com.menuservice.schedule;

import java.util.List;

import org.springframework.stereotype.Component;

import com.menuservice.model.Menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SchduleRequest {
	
	private String timetableId;

	private String emailId;

	private String firstName;
	
	List<Menu> menuItems;

}
