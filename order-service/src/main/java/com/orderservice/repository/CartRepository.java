package com.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderservice.model.Cart;



@Repository
public interface CartRepository extends MongoRepository<Cart, Long> {

	@Query(value="{'cartId':?0}")
	List<Cart> getCart(long cartId);

	@Query(value = "{emailId : ?0}")
	List<Cart> findByEmailId(String emailId);
	
	
	@DeleteQuery
	void deleteByEmailId(String emailId);

}
