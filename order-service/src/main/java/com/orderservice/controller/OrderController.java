package com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderservice.model.Order;
import com.orderservice.service.OrderService;

@RestController
@RequestMapping("v1/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/*
	 * This method is used to add the orders.
	 * 
	 * @Request Body is used to map the json content to java object
	 */
	@PostMapping(value = "add")
	public ResponseEntity<Object> addOrder(@RequestBody Order order) {

		return new ResponseEntity<Object>(orderService.addOrder(order), HttpStatus.OK);
	}

	/*
	 * This method is used to update the order status, value and the food list and
	 * if user not exists it will throw OrderNotFound exception.
	 */
	@PutMapping(value = "order/{orderId}")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long orderId) {
		return new ResponseEntity<Order>(orderService.updateById(orderId, order), HttpStatus.OK);
	}

	/*
	 * This method is used to get existing order and if order not exists it will
	 * throw OrderNotFound exception
	 */
	@GetMapping(value = "order/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
		return new ResponseEntity<Order>(orderService.findById(orderId), HttpStatus.OK);
	}

	/*
	 * This method is used to delete the order and if the order is not found then it
	 * will throw OrderNotFoundException.
	 */
	@DeleteMapping(value = "order/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
		orderService.deleteById(orderId);
		return new ResponseEntity<String>("Order deleted successfully", HttpStatus.OK);

	}

	/*
	 * This method is used to get all the orders.
	 */
	@GetMapping(value = "allorder")
	public ResponseEntity<List<Order>> getAllOrder() {
		return new ResponseEntity<List<Order>>(orderService.getAllOrder(), HttpStatus.OK);

	}

	@GetMapping(value = "orderByEmail/{emailId}")
	public ResponseEntity<List<Order>> getCartFromEmail(@PathVariable String emailId) {
		return new ResponseEntity<>(orderService.getOrderByEmail(emailId), HttpStatus.OK);
	}

}
