package com.subscriptionservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.subscriptionservice.model.Subscription;
@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String>{
	
}
