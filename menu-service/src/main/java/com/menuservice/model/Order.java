package com.menuservice.model;

import java.util.List;

import lombok.Data;

@Data
public class Order {

	private String emailId;
	private List<Menu> menuItems;

}
