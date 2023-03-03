package com.notificationservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificationservice.modal.MailResponse;
import com.notificationservice.modal.OrderEmail;
import com.notificationservice.service.OrderEmailService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RefreshScope
@RequestMapping("/api/v1")
public class OrderEmailController {

	@Autowired
	OrderEmailService emailService;

	@PostMapping("/sendOrderEmail")
	ResponseEntity<MailResponse> sendEmail(@RequestBody OrderEmail orderEmail) {
		Map<String,Object> model = new HashMap<>();
		model.put("firstName", orderEmail.getFirstName());
		model.put("foodList", orderEmail.getOrderList());
		model.put("orderId", orderEmail.getOrderId());
		model.put("orderValue", orderEmail.getOrderValue());
		log.info("Setting up modelmap and email details to pass in service class");
		return new ResponseEntity<>(emailService.sendEmail(orderEmail, model), HttpStatus.OK);
	}
}
