package com.orderservice.model;

import java.time.LocalDateTime;  
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import com.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "order_details")
public class Order {
	@Transient
	public static final String SEQUENCE_NAME = "order_sequence";
	@Id
	private Long orderId;
	List<Menu> foodlist;
	private double value;
	private LocalDateTime orderDate;

	private OrderStatus orderStatus;
	private String emailId;

}
