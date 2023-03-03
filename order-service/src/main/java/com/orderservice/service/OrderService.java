package com.orderservice.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.orderservice.model.Order;

@Service
public interface OrderService {
	 Order addOrder(Order order);

	 List<Order> getAllOrder();

	 Order updateById(Long orderId, Order order);

	 Order findById(Long orderId);

	 void deleteById(Long orderId);
	 
	 List<Order> getOrderByEmail(String emailId);

}
