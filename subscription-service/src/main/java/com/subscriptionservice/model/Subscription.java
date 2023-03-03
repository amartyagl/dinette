package com.subscriptionservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "subscription_details")
@Data
public class Subscription {

	@Id
	private String emailId;
	private String firstName;
	private String lastName;
	private String subscriptionId;
	private String subscriptionName;
	private double subscriptionPrize;
	private String subscriptionDescription;
	private String subscriptionImage;
	private LocalDateTime purchaseDate;
	private LocalDateTime endDate;
	

}