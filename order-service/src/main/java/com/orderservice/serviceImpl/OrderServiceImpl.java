package com.orderservice.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.orderservice.enums.OrderStatus;
import com.orderservice.exception.OrderNotFoundException;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RefreshScope
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	Environment environment;
	
	@Autowired
	SequenceGeneratorService service;

	@Override
	public Order addOrder(Order order) {
		log.info("Order has been added successfully.");
		order.setOrderId(service.getSequenceNumber(Order.SEQUENCE_NAME));
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.COMPLETED);
		Order savedOrder = orderRepository.save(order);
		return savedOrder;
	}

	@Override
	public Order updateById(Long orderId, Order order) {
		log.info("Order has been updated successfully.");
		Optional<Order> orderFromDb = orderRepository.findById(orderId);
		Order updateOrder = null;
		if (orderFromDb.isPresent()) {
			Order orderFromRepo = orderFromDb.get();
			orderFromRepo.setOrderStatus(order.getOrderStatus());
			orderFromRepo.setValue(order.getValue());
			orderFromRepo.setFoodlist(order.getFoodlist());
			orderFromRepo.setOrderDate(LocalDateTime.now());

			updateOrder = orderRepository.save(orderFromRepo);
		} else {
			log.info("Order is not found for given Id");
			throw new OrderNotFoundException("SERVICE.ORDER_NOT_FOUND");
		}
		return updateOrder;

	}

	@Override
	public Order findById(Long orderId) {
		log.info("Order is found by id");
		Optional<Order> orderFromRepo = orderRepository.findById(orderId);
		if (orderFromRepo.isPresent()) {
			return orderFromRepo.get();
		} else {
			log.info("Order is not found for given Id");
			throw new OrderNotFoundException("SERVICE.ORDER_NOT_FOUND");
		}
	}

	@Override
	public void deleteById(Long orderId) {
		log.info("Order is deleted by id.");
		Optional<Order> orderFromRepo = orderRepository.findById(orderId);
		if (orderFromRepo.isPresent()) {
			orderRepository.deleteById(orderId);
		} else
			log.info("Order is not found for given Id");
		throw new OrderNotFoundException("SERVICE.ORDER_NOT_FOUND");
	}

	@Override
	public List<Order> getAllOrder() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrderByEmail(String emailId) {
		List<Order> getByEmail = orderRepository.findByEmailId(emailId.toLowerCase());

		return getByEmail;

	}

}
