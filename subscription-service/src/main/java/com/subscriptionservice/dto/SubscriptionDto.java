package com.subscriptionservice.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.subscriptionservice.constant.Constant;

import lombok.Data;

@Data
public class SubscriptionDto {
	
	@NotEmpty(message = Constant.MESSAGE_EMPTYEMAILID)
	@Email(message=Constant.MESSAGE_INVALIDEMAILID)
	private String emailId;
	
	@NotBlank(message =Constant.MESSAGE_FIRSTNAME) 
	private String firstName;
	private String lastName;
	String subscriptionId;
	@NotBlank(message =Constant.MESSAGE_SUBSCRIPTIONNAME) 
	String subscriptionName;
	double subscriptionPrize;
	String subscriptionDescription;
	String subscriptionImage;
	
}
