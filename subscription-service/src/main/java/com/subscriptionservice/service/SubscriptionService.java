package com.subscriptionservice.service;

import com.subscriptionservice.dto.SubscriptionDto;
import com.subscriptionservice.exception.SubscriptionAlreadyExistException;
import com.subscriptionservice.exception.SubscriptionNotFoundException;

public interface SubscriptionService {

	SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) throws SubscriptionAlreadyExistException;
	
	SubscriptionDto upgradeSubscription(SubscriptionDto subscriptionDto) throws SubscriptionNotFoundException;

	void deleteSubscription(String emailId) throws SubscriptionNotFoundException;
	
	
}
