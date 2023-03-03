package com.orderservice.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orderservice.model.Menu;
import com.orderservice.model.Order;
import com.orderservice.serviceImpl.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Consumer {

	@Autowired
	OrderServiceImpl orderServiceImpl;

	@RabbitListener(queues = "order_queue")
	public void getOrderFromRabbitMq(OrderDTO orderDto) {

		double value = 0.0;
		List<Menu> menuItems = orderDto.getMenuItems();
		Iterator<Menu> menuIterator = menuItems.iterator();
		while (menuIterator.hasNext()) {
			Menu menu = menuIterator.next();
			Collection<Double> priceCollection = menu.getFoodSize().values();
			Double[] prices = new Double[priceCollection.size()];
			prices = priceCollection.toArray(prices);
			for (double price : prices) {
				value += price;
			}
		}
		Order order = new Order();
		order.setEmailId(orderDto.getEmailId());
		order.setFoodlist(orderDto.getMenuItems());
		order.setValue(value);
		log.info("Adding autoscheduled order from timetable service to order service");
		orderServiceImpl.addOrder(order);

	}

}
