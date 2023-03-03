package com.paymentservice.model;

import java.io.Serializable;

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
@Document(collection = "Payments")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String myPaymentId;
	private String paymentId;
	private String orderId;
	private String emailId;
	private int amount;
	private String status;
	private String receipt;

}
