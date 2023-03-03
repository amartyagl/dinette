package com.menuservice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TimeTable")
public class TimeTable {

	public enum Day {
		MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THURSDAY("thursday"), FRIDAY("friday"),
		SATURDAY("saturday"),SUNDAY("sunday");

		public String name;

		Day(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	private String timetableId;

	private String emailId;

	private String firstName;

	private String lastName;

	private Map<Day, List<Menu>> menuItems = new HashMap<>();

}
