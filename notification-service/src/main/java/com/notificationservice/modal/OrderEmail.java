package com.notificationservice.modal;

import java.util.List;
import lombok.Data;

@Data
public class OrderEmail {
	private String emailId;
	private String orderId;
	private String firstName;
	private List<Food> orderList;
	private double orderValue;

}
