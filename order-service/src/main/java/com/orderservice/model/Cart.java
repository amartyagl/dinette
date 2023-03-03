package com.orderservice.model;

import java.util.List;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cart_details")
public class Cart {
	@Transient
	public static final String SEQUENCE_NAME = "cart_sequence";
	@Id
	private long cartId;
	private int quantity;
	private double value;
	private List<Menu> food;
	private String emailId;

}
