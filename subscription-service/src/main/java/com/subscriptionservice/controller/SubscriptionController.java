package com.subscriptionservice.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.subscriptionservice.dto.SubscriptionDto;
import com.subscriptionservice.exception.SubscriptionAlreadyExistException;
import com.subscriptionservice.exception.SubscriptionNotFoundException;
import com.subscriptionservice.service.SubscriptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;
	
	@PostMapping("/addSubscription")
	ResponseEntity<SubscriptionDto> addSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) throws SubscriptionAlreadyExistException, Exception {
		try {
			return new ResponseEntity<SubscriptionDto>(subscriptionService.addSubscription(subscriptionDto), HttpStatus.CREATED);
		} catch (SubscriptionAlreadyExistException e) {
			throw new SubscriptionAlreadyExistException(e.getMessage());
		} catch (Exception e) {
			log.error("Error while adding subscription", e.getMessage());
			throw new Exception();
		}
	}
	
	@PutMapping("/upgrade/{emailId}")
	ResponseEntity<SubscriptionDto> upgradeSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) throws SubscriptionNotFoundException, Exception{
		try {
			return new ResponseEntity<SubscriptionDto>(subscriptionService.upgradeSubscription(subscriptionDto), HttpStatus.OK);
		} catch (SubscriptionNotFoundException e){
			throw new SubscriptionNotFoundException(e.getMessage());
		}catch (Exception e) {
		log.error("Subscription not found to upgrade", e.getMessage());}
		throw new Exception();	
	}
	
	@DeleteMapping("/deleteSubscription/{emailId}")
	ResponseEntity<String> deleteSubscription(@PathVariable String emailId){
		subscriptionService.deleteSubscription(emailId);
		return new ResponseEntity<String>("Subscription deleted successfully", HttpStatus.OK);
	}

}
