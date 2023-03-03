package com.paymentservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.paymentservice.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {
	
	@Query(value = "{orderId : ?0}")
	Payment  findByOrderId(String orderId);

	@Query(value = "{emailId : ?0}")
	List<Payment> findByEmail(String emailId);

}
