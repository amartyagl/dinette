
package com.menuservice.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.menuservice.model.Menu;
import com.menuservice.model.TimeTable.Day;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDto {

	private String timetableId;
	
	@NotEmpty(message = "email is required")
	@Email(message = "enter valid")
	private String emailId;

	@NotNull(message = "Name is required")
	private String firstName;

	private String lastName;

	private Map<Day, List<Menu>> menuItems = new HashMap<>();

}
