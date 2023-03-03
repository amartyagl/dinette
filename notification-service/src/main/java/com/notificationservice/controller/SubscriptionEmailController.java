package com.notificationservice.controller;

import java.time.LocalDate;
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
import com.notificationservice.modal.SubscriptionEmail;
import com.notificationservice.service.SubscriptionEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RefreshScope
@RequestMapping("/api/v1")
public class SubscriptionEmailController {

	@Autowired
	SubscriptionEmailService emailService;

	@PostMapping("/sendSubscriptionEmail")
	ResponseEntity<MailResponse> sendEmail(@RequestBody SubscriptionEmail subscriptionEmail) {
		LocalDate date = LocalDate.now();
		Map<String, Object> model = new HashMap<>();
		model.put("name", subscriptionEmail.getName());
		model.put("subscriptionId", subscriptionEmail.getSubscriptionId());
		model.put("subscriptionName", subscriptionEmail.getSubscriptionName());
		model.put("subscriptionDesc", subscriptionEmail.getSubscriptionDescription());
		model.put("subscriptionImage", subscriptionEmail.getSubscriptionImage());
		model.put("subscriptionPrice", subscriptionEmail.getSubscriptionPrice());
		model.put("endDate", subscriptionEmail.getEndDate());
		model.put("purchaseDate", date);
		log.info("Setting up modelmap and email details to pass in service class");
		return new ResponseEntity<>(emailService.sendEmail(subscriptionEmail, model), HttpStatus.OK);
	}

}
