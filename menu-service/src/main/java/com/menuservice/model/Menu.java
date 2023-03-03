package com.menuservice.model;

import java.io.Serializable;
import java.util.ArrayList;
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
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "Menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum SubscriptionCategory {
		SILVER("silver"), PLATINUM("platinum"), GOLD("gold");

		private String name;

		SubscriptionCategory(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	private String foodItemId;

	private String foodName;

	private String foodPicture;

	private boolean foodAvailability;

	private String foodDescription;

	private List<String> foodCategory = new ArrayList<>();
	
	private Map<String, Double> foodSize = new HashMap<>();

	private List<SubscriptionCategory> subscriptionCategory = new ArrayList<>();

}
