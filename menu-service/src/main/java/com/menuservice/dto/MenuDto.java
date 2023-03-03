
package com.menuservice.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;
import com.menuservice.model.Menu.SubscriptionCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

	private String foodItemId;

	@NotNull(message = "food name is required")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "name must be a string")
	private String foodName;

	private String foodPicture;

	@NotNull(message = "food availability is required")
	private boolean foodAvailability;

	@NotNull(message = "food description is required")
	private String foodDescription;

	private List<String> foodCategory = new ArrayList<>();

	private Map<String, Double> foodSize = new HashMap<>();

	@NotEmpty(message = "subscription category is required")
	private List<SubscriptionCategory> subscriptionCategory;

}
