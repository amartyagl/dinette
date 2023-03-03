package com.paymentservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PaymentDto {

	private String myPaymentId;
	private String paymentId;
	private String orderId;

	@NotEmpty(message = "email is required")
	@Email(message = "enter valid email address")
	private String emailId;

	private int amount;

	private String status;
	
	private String receipt;

}
