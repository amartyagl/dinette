package com.menuservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.menuservice.model.Menu;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
	
	@Query(value = "{foodName : ?0}")
	Optional<Menu> findByName(String foodName);
	
	@Query(value = "{subscriptionCategory : ?0}")
	List<Menu> findBySubscriptionCategory(String category);
	
	

}
