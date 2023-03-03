package com.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import com.orderservice.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

	@Query(value = "{emailId : ?0}")
	List<Order> findByEmailId(String emailId);
}
