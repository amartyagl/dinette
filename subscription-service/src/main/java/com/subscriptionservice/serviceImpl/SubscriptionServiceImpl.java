package com.subscriptionservice.serviceImpl;

import java.time.LocalDateTime;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subscriptionservice.dto.SubscriptionDto;
import com.subscriptionservice.exception.SubscriptionAlreadyExistException;
import com.subscriptionservice.exception.SubscriptionNotFoundException;
import com.subscriptionservice.model.Subscription;
import com.subscriptionservice.repository.SubscriptionRepository;
import com.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionRepository subscriptionRepository;

	@Override
	public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) throws SubscriptionAlreadyExistException {
		if (subscriptionRepository.existsById(subscriptionDto.getEmailId())) {
			log.error("Subscription is already active.");
			throw new SubscriptionAlreadyExistException("SERVICE.SUBSCRIPTION_ALREADY_EXIST");
		} else {
			Subscription newSubscription = new Subscription();
			newSubscription.setFirstName(subscriptionDto.getFirstName());
			newSubscription.setLastName(subscriptionDto.getLastName());
			newSubscription.setEmailId(subscriptionDto.getEmailId());
			newSubscription.setSubscriptionId(subscriptionDto.getSubscriptionId());
			newSubscription.setSubscriptionName(subscriptionDto.getSubscriptionName());
			newSubscription.setSubscriptionPrize(subscriptionDto.getSubscriptionPrize());
			newSubscription.setSubscriptionDescription(subscriptionDto.getSubscriptionDescription());
			newSubscription.setPurchaseDate(LocalDateTime.now());

			subscriptionRepository.save(newSubscription);
			log.info("Subscription is successfully added");
			return subscriptionDto;

		}
	}

	@Override
	public SubscriptionDto upgradeSubscription(SubscriptionDto subscriptionDto) throws SubscriptionNotFoundException {
		Subscription upgradedSubscription = new Subscription();
		Optional<Subscription> findById = subscriptionRepository.findById(subscriptionDto.getEmailId());
		if (findById.isPresent()) {
			Subscription subscriptionToUpdate = findById.get();
			upgradedSubscription.setSubscriptionId(subscriptionDto.getSubscriptionId());
			upgradedSubscription.setSubscriptionName(subscriptionToUpdate.getSubscriptionName());
			subscriptionRepository.save(upgradedSubscription);
			log.info("Subscription upgraded");
			return subscriptionDto;
		} else {
			log.error("Subscription not found to upgrade");
			throw new SubscriptionNotFoundException("SERVICE.SUBSCRIPTION_NOT_FOUND");
		}

	}

	@Override
	public void deleteSubscription(String emailId) {
		/*
		 * Optional<String> subscription =
		 * subscriptionRepository.findById(subscriptionId); if(subscription.isPresent())
		 * { subscriptionRepository.deleteById(subscriptionId); }else {
		 * log.info("Subscription is not found"); throw new
		 * SubscriptionNotFoundException("SERVICE.SUBSCRIPTION_NOT_FOUND"); }
		 */
		Optional<Subscription> subscriptionFromRepo = subscriptionRepository.findById(emailId);
		if (subscriptionFromRepo.isPresent()) {
			subscriptionRepository.deleteById(emailId);
			
		} else {
			log.info("Subscription is not found for given Id");
			throw new SubscriptionNotFoundException("SERVICE.SUBSCRIPTION_NOT_FOUND");
	}
	}
}
