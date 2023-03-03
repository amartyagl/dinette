package com.orderservice.config;

import java.util.List;

import com.orderservice.model.Menu;

import lombok.Data;

@Data
public class OrderDTO {
	private String emailId;
	private List<Menu> menuItems;
}
